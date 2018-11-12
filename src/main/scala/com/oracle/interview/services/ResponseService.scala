package com.oracle.interview.services

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

final case class GeneralResponse(statusCode: Int, message: String)
final case class UserResponse(id: Long, firstName: String, lastName: String)
final case class LocationResponse(id: Long, name: String)
final case class InterviewResponse(id: Long, startTime: Long, duration: Long, location: LocationResponse,
                                   interviewer: UserResponse, candidate: UserResponse)

trait ResponseService extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val generalResponseFormat: RootJsonFormat[GeneralResponse] = jsonFormat2(GeneralResponse)
  implicit val userResponseFormat: RootJsonFormat[UserResponse] = jsonFormat3(UserResponse)
  implicit val locationResponseFormat: RootJsonFormat[LocationResponse] = jsonFormat2(LocationResponse)
  implicit val interviewResponse: RootJsonFormat[InterviewResponse] = jsonFormat6(InterviewResponse)
}