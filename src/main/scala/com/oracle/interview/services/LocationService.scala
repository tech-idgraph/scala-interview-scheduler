package com.oracle.interview.services

import com.oracle.interview.models.Location
import com.oracle.interview.schemas.Locations
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LocationService {

  def insert(name: String, db: H2Profile.api.Database): Future[Int] = {
    val locations: TableQuery[Locations] = Location
    val locQuery = locations.sortBy(_.locationId.desc).take(1)

    db.run(locQuery.result).flatMap(result => {
      val locId: Long = result.headOption match {
        case Some(lastLocId) => lastLocId.locationId + 1
        case None => 0
      }
      val newLocation: Location = Location(locId, name)

      val insertion = locations.insertOrUpdate(newLocation)

      db.run(insertion)
    })
  }

  def update(location: Location, db: H2Profile.api.Database): Future[Int] = {
    val locations: TableQuery[Locations] = Location
    val locUpdate = locations.filter(l => l.locationId === location.locationId).update(location)

    db.run(locUpdate)
  }

  def delete(locationId: Long, db: H2Profile.api.Database): Future[Int] = {
    val locations: TableQuery[Locations] = Location
    val deletion = locations.filter(l => l.locationId === locationId).delete

    db.run(deletion)
  }

  def read(locationId: Long, db: H2Profile.api.Database): Future[Option[Location]] = {
    val locations: TableQuery[Locations] = Location
    val readLocation = locations.filter(l => l.locationId === locationId).result

    db.run(readLocation.headOption)
  }
}
