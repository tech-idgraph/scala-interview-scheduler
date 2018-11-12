package com.oracle.interview.services

import com.oracle.interview.models.{Interview, Location, User}
import com.oracle.interview.schemas.{Interviews, Locations, Users}
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object InterviewService {
  def insert(startTimeSecs: Long, durationSecs: Long, locationId: Long, interviewerId: Long,
             candidateId: Long, db: H2Profile.api.Database): Future[Int] = {

    val interviews: TableQuery[Interviews] = Interview
    val idQuery = interviews.sortBy(_.interviewId.desc).take(1)

    db.run(idQuery.result).flatMap(result => {
      val uid: Long = result.headOption match {
        case Some(lastId) => lastId.interviewId + 1
        case None => 0
      }
      val newInterview: Interview = Interview(uid, startTimeSecs, durationSecs, locationId, interviewerId, candidateId)

      val insertion = interviews.insertOrUpdate(newInterview)

      db.run(insertion)
    })
  }

  def update(interview: Interview, db: H2Profile.api.Database): Future[Int] = {
    val interviews: TableQuery[Interviews] = Interview
    val interviewUpdate = interviews.filter(i => i.interviewId === interview.interviewId)
      .update(interview)

    db.run(interviewUpdate)
  }

  def delete(interviewId: Long, db: H2Profile.api.Database): Future[Int] = {
    val interviews: TableQuery[Interviews] = Interview
    val deletion = interviews.filter(i => i.interviewId === interviewId).delete

    db.run(deletion)
  }

  def read(interviewId: Long,
                    db: H2Profile.api.Database): Future[Option[(((Interview, Location), User), User)]] = {
    val interviews: TableQuery[Interviews] = Interview
    val users: TableQuery[Users] = User
    val locations: TableQuery[Locations] = Location

    //val readInterview = interviews.filter(i => i.interviewId === interviewId).result
    val readInterview = ((interviews join locations on {
      case (i, l) => l.locationId === i.locationId
    }).filter {
      case (i, l) => i.interviewId === interviewId
    } join users on {
      case (il, u) => il._1.interviewerId === u.userId
    } join users on {
      case (ilu, u) => {
        ilu._1._1.candidateId === u.userId
      }
    }).result

    db.run(readInterview.headOption)

  }
}
