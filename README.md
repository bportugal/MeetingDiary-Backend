# MeetingDiary-Backend
Server to support APP for tracking user meetings

You can sign up and login users, those users will have a relationship with the Meetings entity.

A meeting is created with an user, the person name that the user met, the meeting date and the geolocation coordinates (latitude and longitude).

It is possible to create multiple meetings with a single call to its endpoint (http://localhost:8080/meeting) passing a list of meetingsDTO objects.

The project is based on a small web service which uses the following technologies:

* Java 11
* Spring Boot
* Database H2 (In-Memory)
* Maven

On http://localhost:8080/swagger-ui/#/ you can find all documentation regarding endpoints and parameters to be passed on each.

On http://localhost:8080/h2-console/ you can look into the In-Memory Database.
