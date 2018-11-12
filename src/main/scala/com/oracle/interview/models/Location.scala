package com.oracle.interview.models

import com.oracle.interview.schemas.Locations
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery


case class Location(locationId: Long, name: String)

object Location extends TableQuery(new Locations(_)) {
  val findByLocationId = this.findBy(col => col.locationId)
}

