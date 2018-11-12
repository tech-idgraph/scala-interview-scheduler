package com.oracle.interview

import akka.http.javadsl.common.JsonEntityStreamingSupport
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import com.oracle.interview.models.{Interview, User}
import com.oracle.interview.services._


object ServiceRoutes extends Directives with ResponseService {
  def user: Route = path("services/user") {
    post {
      formFields('firstName, 'lastName) { (firstName, lastName) => {
        onSuccess(UserService.insert(firstName, lastName, Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.OK.intValue, "Successfully added new user."))
        }
      }}
    }
    put {
      entity(as[UserResponse]) { user =>
        onSuccess(UserService.update(User(user.id, user.firstName, user.lastName), Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.OK.intValue, s"Successfully updated user ${user.id}."))
        }
      }
    }
    path(Segment) { userId =>
      get {
        onSuccess(UserService.read(userId.toLong, Repository.db)) { extraction =>
          extraction match {
            case Some(foundUser) => complete(UserResponse(foundUser.userId, foundUser.lastName, foundUser.lastName))
            case None => complete(GeneralResponse(StatusCodes.BadRequest.intValue, s"No user with $userId found."))
          }

        }
      }
      delete {
        onSuccess(UserService.delete(userId.toLong, Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.NoContent.intValue, s"Successfully deleted user $userId."))
        }
      }
    }
  }

  def interview: Route = path("services/user") {
    post {
      formFields('startTime.as[Long], 'duration.as[Long], 'locationId.as[Long], 'interviewerId.as[Long],
        'candidate.as[Long]) { (startTime, duration, locationId, interviewerId, candidateId) => {
        onSuccess(InterviewService.insert(startTime, duration, locationId, interviewerId, candidateId,
          Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.OK.intValue, "Successfully added new interview."))
        }
      }}
    }
    put {
      entity(as[InterviewResponse]) { interview =>
        onSuccess(InterviewService.update(Interview(interview.id, interview.startTime, interview.duration,
          interview.location.id, interview.interviewer.id, interview.candidate.id), Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.OK.intValue, s"Successfully updated interview ${interview.id}."))
        }
      }
    }
    path(Segment) { interviewId =>
      get {
        onSuccess(InterviewService.read(interviewId.toLong, Repository.db)) { extraction =>
          extraction match {
            case Some(foundInterview) => {
              val location: LocationResponse = LocationResponse(foundInterview._1._1._2.locationId,
                foundInterview._1._1._2.name)
              val interviewer: UserResponse = UserResponse(foundInterview._1._2.userId, foundInterview._1._2.firstName,
                foundInterview._1._2.lastName)
              val candidate: UserResponse = UserResponse(foundInterview._2.userId, foundInterview._2.firstName,
                foundInterview._2.lastName)

              complete(InterviewResponse(foundInterview._1._1._1.interviewId, foundInterview._1._1._1.startTimeSecs,
                foundInterview._1._1._1.durationSecs, location, interviewer, candidate))
            }
            case None => complete(GeneralResponse(StatusCodes.BadRequest.intValue, s"No user with $interviewId found."))
          }

        }
      }
      delete {
        onSuccess(UserService.delete(interviewId.toLong, Repository.db)) { extraction =>
          complete(GeneralResponse(StatusCodes.NoContent.intValue, s"Successfully deleted user $interviewId."))
        }
      }
    }
  }
}
