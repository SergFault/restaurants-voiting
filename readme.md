REST "restaurant-voting" Project
===============================
Simple REST service with extensive domain model with authentication and authorization (Spring Security).
Persistence into DB implemented with JPA Hibernate.

Tech stack: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), 
Java 8 Stream and Time API, storage -> HSQLDB.

Application implements restaurants ranking functionality
with users and their voices with value one to ten. Two types of user 
(admin, user) Users can leave their vote for each restaurant before 11 a.m.