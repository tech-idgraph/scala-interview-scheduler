package com.oracle.interview

import com.oracle.interview.models.{Location, Interview, User}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

object Repository {
  val db: Database = Database.forConfig("h2mem1")

  def init(): Future[Unit] = {
    db.run(setup)
  }

  private def setup: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(
    (User.schema ++ Interview.schema ++ Location.schema).create
  )

}
