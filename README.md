# Web Quiz Engine
> A multi-user web service for creating and solving quizzes using REST API

## General info
This web application provides a platform for storing and sharing multiple-choice quizzes. The functionality covers creating new quizzes, getting them by id, getting a list of all quizzes, solving quizzes, getting a list of completed quizzes by a certain user as well as updating and deleting them. All of these operations are available via HTTP requests (GET, POST, PUT and DELETE). To ensure that the service does not accept incorrect quizzes, bean validation is applied. Security is provided by user authorization. To perform any operations with quizzes, the user has to be registered and then authorized with HTTP Basic Auth by sending their email and password for each request. All of the quizzes and user data are stored in H2 database using Spring Data JPA. For security reasons all passwords are encrypted with BCrypt algorithm. In case of big amount of qiuzzes in database, the service supports pagination.

## Features
| Function  | Method | API |
| ------------- | ------------- | ------------- |
| Get all the quizzes  | GET  | 	/api/quizzes|
| Get all the completed quizzes  | GET  | 	/api/quizzes/completed|
| Get a specific quiz with ID | GET | 	/api/quizzes/{id} |
| Create a quiz | POST  | 	/api/quizzes |
| Solve a quiz | POST | /api/quizzes/{id}/solve |
| Register a new user | POST | /api/register |
| Update a quiz | PUT | /api/quizzes/{id} |
| Delete a quiz  | DELETE  | /api/quizzes/{id} |

## Technologies
* Java SE 11
* Spring Boot 2.4.10
  - Spring Boot Starter
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Validation
  - Spring Boot Starter Actuator
* H2 Database Engine 1.4.200
* Maven 3.6.3
