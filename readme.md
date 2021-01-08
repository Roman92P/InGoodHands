# Oddam w dobre ręce/ In good hands
## Inspiration
Everyone knows that number of things that we possess increases every year. Finally we beging thinking what can we do with all of these stuff.
Often we don't even need to posses so much stuff. On the other hand there are a lot of others who really need to have these things. 
This web application allows user to hand over unnecessary things to people who really need them. 
### Features
#### For User
* Registration (email verification)
* Reset password
* Complete donation through fill down form (just few simple steps)
* View all completed donation (all, shipped, not shipped)
* Ability to change status of donations
* Ability to change user profile details
#### For Admin
* institution CRUD
* User CRUD
* Admin CRUD
### Technologies
* Java
* CSS
* Thymeleaf Engine
* JPQL
* JavaScript
* Spring Boot
* Hibernate
* SQL
* Bootstrap
### How to run
* First of all you need to instal maven if you don’t have.
* Copy zip with project from repository
* Create your DB, for example with Workbench
* Go to \src\main\resources - set your password and user name to db.
  * spring.datasource.username=[put name here]
  * spring.datasource.password=[put password here]
* Set gmial address and password to be able send e-mails:
  * spring.mail.username=[put your gmail address]
  * spring.mail.password=[put your gmail password]
* Navigate to the root of the project via command line and execute the command
  * mvn spring-boot:run
* Open your browser and use url: http://localhost:8080/
### Demo
![](gifdemo.gif)
