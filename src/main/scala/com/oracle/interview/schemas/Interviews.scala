package com.oracle.interview.schemas

import com.oracle.interview.models.{Location, Interview, User}
import slick.jdbc.H2Profile.api._

class Interviews(tag: Tag) extends Table[Interview](tag, "INTERVIEWS") {
  val locations: TableQuery[Locations] = Location
  val users: TableQuery[Users] = User

  def interviewId: Rep[Long] = column[Long]("interviewId", O.PrimaryKey)
  def startTimeSec: Rep[Long] = column[Long]("startTimeSecs")
  def durationSecs: Rep[Long] = column[Long]("durationSecs")
  def locationId: Rep[Long] = column[Long]("locationId")
  def interviewerId: Rep[Long] = column[Long]("interviewerId")
  def candidateId: Rep[Long] = column[Long]("candidateId")

  def location = foreignKey("LOCATION_FK", locationId, locations)(_.locationId, onUpdate=ForeignKeyAction.Restrict,
    onDelete=ForeignKeyAction.Cascade)
  def interviewer = foreignKey("INTERVIEWER_FK", interviewerId, users)(_.userId, onUpdate=ForeignKeyAction.Restrict,
    onDelete=ForeignKeyAction.Cascade)
  def candidate = foreignKey("INTERVIEWER_FK", candidateId, users)(_.userId, onUpdate=ForeignKeyAction.Restrict,
    onDelete=ForeignKeyAction.Cascade)

  override def * = (interviewId, startTimeSec, durationSecs, locationId, interviewerId, candidateId) <>
    ((Interview.apply _).tupled, Interview.unapply)
}
