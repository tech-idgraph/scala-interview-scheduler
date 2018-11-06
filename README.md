# scala-interview-scheduler
## The most awesome interview scheduling REST API the world has ever seen...

### What you need to implement
* Data models
* The service layer, using Akka HTTP
* The Database layer (H2 In Memory DB), using [Lightbend Slick](http://slick.lightbend.com/doc/3.2.3/)
* Unit tests

#### REST Endpoints (All should accept and return JSON)
* /service/user
  * POST, PUT, DELETE, GET
* /service/location
  * POST, PUT, DELETE, GET
* /service/interview
  * POST, PUT, DELETE, GET
* /service/schedule
  * GET
  
#### Data Models
* User => Define a user for the system, there are two types an interviewer and a candidate.
* Locations => Locations (Meeting rooms) needs to be created for interviews to take place in.
* Interviews => Define a time and place as well as attendees, these should be links to other parts of the system.
* Schedule => A listing of all active meetings in the system.

### Aceptance Criteria
The main goal of this project is the schedule interviews in the system. You should implement all system data models and all REST endpoints with their corresponding verbs. Also you should have an H2 in memory database which is created each time the service layer starts up. Ideally unit tests should be created to test the critical parts of your system. Please submit a merge/pull request once you are happy with the final state of your code.
