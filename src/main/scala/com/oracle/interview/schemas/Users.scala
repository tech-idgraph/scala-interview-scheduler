package com.oracle.interview.schemas


import com.oracle.interview.models.User
import slick.jdbc.H2Profile.api._

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def userId: Rep[Long] = column[Long]("USER_ID", O.PrimaryKey)
  def firstName: Rep[String] = column[String]("FIRST_NAME")
  def lastName: Rep[String] = column[String]("LAST_NAME")

  override def * = (userId, firstName, lastName) <> ((User.apply _).tupled, User.unapply)
}


