package com.oracle.interview.models

import com.oracle.interview.schemas.Users
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._


case class User(userId: Long, firstName: String, lastName: String)

object User extends TableQuery(new Users(_)) {
  val findByUserId = this.findBy(col => col.userId)
}

