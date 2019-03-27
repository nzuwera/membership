# Membership

## Spring membership challenge
### Summary
 Write a REST API for a simplified membership platform
### Purpose
This challenge tests the developer’s familiarity with Spring components like JPA, Spring Beans, Spring Data, JAXRS, Application Servers and the Maven build tool..
### Topic Tags:
 Membership, REST-API, Spring
Applicable language/frameworks: 
### Estimated time: 3 Hours
### Instructions: 
You are building a membership platform. A plan can be unlimited or time limited. If time limited, the plan must have a start date and an end date. All plans must have a name. A member can be added to a plan. The member must have a first name, last name and date of birth. We should be able to list members by the plan they belong to.

### Task Specification
Using TDD implement the above datamodel as a web service with REST endpoints. If you have experience with MongoDB, please use that as your preferred database. If not, feel free to use MySQL or Postgresql
Using profiles show how to switch two types of `properties` files
Use maven to configure and deploy to an application server of your own choice.

#### Answers
The endpoint connect to PostgreSQL

##### Plans & Members
You can add a new plan using endpoint `POST /plan/create-plan?planName=Plan001&planType=UNLIMITED`
and get response like
`{
     "status": true,
     "message": "found",
     "data": {
         "id": "5e3e6784-8152-446e-a0b4-4715648c0ab6",
         "name": "Plan002",
         "startDate": "2019-03-27T13:11:21.202+0000",
         "endDate": "2019-04-16T13:11:21.202+0000",
         "type": "LIMITED"
     },
     "errorCode": 200
 }`
You can add a new member using `POST /member/create-member`
with request body
`{
	"firstName":"Nzuwera",
	"lastName":"Gilbert",
	"email":"nzuwera2002@gmail.com",
	"dateOfBirth":"1984-06-26",
	"planName":"Plan001"
}`
And get response
`{
     "id": "2437085f-ae5b-4c89-86b3-e2955af725a3",
     "firstName": "Nzuwera",
     "lastName": "Gilbert",
     "email": "nzuwera2002@gmail.com",
     "dateOfBirth": "1984-06-26T00:00:00.000+0000",
     "plan": {
         "id": "a169286f-e6df-43e5-93ff-98577a170404",
         "name": "Plan001",
         "startDate": "2019-03-27T11:07:54.549+0000",
         "endDate": null,
         "type": "UNLIMITED"
     }
 }`
 
 ##### Endpoint documentation
 
 I have used Spring Rest Docs to document the rest endpoints.
 when you run the command `mvn clean install` it will package and generate and html file located in `target/generated-docs`
 
 ##### Profiles
 I have added 3 profiles developement, uat & local
 you can run the command `mvn clean install -Plocal` to run local profile.
 
 ##### Run the application
 as a spring boot application `mvn spring-boot:run` will run the application 
 or you can run `mvn clean install && java -jar ./target/membership-0.0.1-SNAPSHOT.jar`
 




