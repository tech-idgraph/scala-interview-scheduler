package com.oracle.interview.schemas

import com.oracle.interview.models.{Location, Interview}
import slick.jdbc.H2Profile.api._

class Locations(tag: Tag) extends Table[Location](tag, "LOCATIONS") {
  val interviews: TableQuery[Interviews] = Interview

  def locationId: Rep[Long] = column[Long]("locationId", O.PrimaryKey)
  def name: Rep[String] = column[String]("interviewId")

  override def * = (locationId, name) <> ((Location.apply _).tupled, Location.unapply)
}
