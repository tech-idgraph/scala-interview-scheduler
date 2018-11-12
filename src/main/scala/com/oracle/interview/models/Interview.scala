package com.oracle.interview.models

import com.oracle.interview.schemas.Interviews
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery


case class Interview(interviewId: Long, startTimeSecs: Long, durationSecs: Long, locationId: Long,
                     interviewerId: Long, candidateId: Long)

object Interview extends TableQuery(new Interviews(_)) {
  val findByInterviewId = this.findBy(col => col.interviewId)
}

