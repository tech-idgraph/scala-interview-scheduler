package com.oracle.interview.services

import com.oracle.interview.models.User
import com.oracle.interview.schemas.Users
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object UserService {

  def insert(firstName: String, lastName: String, db: Database): Future[Int] = {
    val users: TableQuery[Users] = User
    val uidQuery= users.sortBy(_.userId.desc).take(1)

    db.run(uidQuery.result).flatMap(result => {
      val uid: Long = result.headOption match {
        case Some(lastUid) => lastUid.userId + 1
        case None => 0
      }
      val newUser: User = User(uid, firstName, lastName)

      val insertion = users.insertOrUpdate(newUser)

      db.run(insertion)
    })
  }

  def update(user: User, db: Database): Future[Int] = {
    val users: TableQuery[Users] = User
    val userUpdate = users.filter(u => u.userId === user.userId)
      .update(user)

    db.run(userUpdate)
  }

  def delete(userId: Long, db: Database): Future[Int] = {
    val users: TableQuery[Users] = User
    val deletion = users.filter(u => u.userId === userId).delete

    db.run(deletion)
  }

  def read(userId: Long, db: Database): Future[Option[User]] = {
    val users: TableQuery[Users] = User
    val readUser = users.filter(u => u.userId === userId).result

    db.run(readUser.headOption)
  }
}
