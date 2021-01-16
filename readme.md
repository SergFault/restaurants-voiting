REST "restaurant-voting" Project
===============================
Simple REST service with extensive domain model with authentication and authorization (Spring Security).
Persistence into DB implemented with JPA Hibernate.

A tech stack: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), 
Java 8 Stream and Time API, storage -> HSQLDB.

Application implements restaurants ranking functionality.

Application supports 2 types of users: admin and regular users.

Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price).
Users can leave their vote for each restaurant (with value of 1 to 10.). They can change their voce same day but only before 11 a.m.
Users can check a particular restaurant and it`s average rank by rest request.

# A relative path for admin types of operations: "/rest/admin"

## Update menu for particular restaurant:

curl --location --request PUT 'http://localhost:8080/rest/admin/restaurants/10020/menu' \  
--header 'Authorization: Basic ZXVnZW5lQG1haWwuY29tOnBhc3N3b3Jk' \  
--header 'Content-Type: application/json' \  
--data-raw '[  
{  
"name": "Беляш2",  
"price": 10000,  
"date": "2019-11-11T21:00:00.000+00:00"  
},  
{  
"name": "Бигос3",  
"price": 12000,  
"date": "2019-11-11T21:00:00.000+00:00"  
},  
{  
"name": "Бисквит4",  
"price": 5000,  
"date": "2019-11-11T21:00:00.000+00:00"  
},  
{  
"name": "Винегрет53",  
"price": 7000,  
"date": "2019-11-11T21:00:00.000+00:00"  
}  
]  
'  

10020 - id of restaurant to change menu in.

## Create a new restaurant:

curl --location --request POST 'http://localhost:8080/rest/admin/restaurants' \  
--header 'Authorization: Basic ZXVnZW5lQG1haWwuY29tOnBhc3N3b3Jk' \  
--header 'Content-Type: application/json' \  
--data-raw ' {  
"name": "Кураж",  
"menu": [  
{  
"name": "Рис",  
"price": 45000  
},  
{  
"name": "Шашлык",  
"price": 33000  
},  
{  
"name": "Шницель",  
"price": 30500  
},  
{  
"name": "Антрекот",  
"price": 13500  
}  
]  
}'  

## Update restaurant that already exists:

curl --location --request PUT 'http://localhost:8080/rest/admin/restaurants/10020' \  
--header 'Authorization: Basic ZXVnZW5lQG1haWwuY29tOnBhc3N3b3Jk' \  
--header 'Content-Type: application/json' \  
--data-raw ' {  
"name": "Новый ресторан",  
"menu": [  
{  
"name": "Картофель",  
"price": 45000  
},  
{  
"name": "Солянка",  
"price": 33000  
}  
]  
}'  

## Response Headers
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
Date: Fri, 15 Jan 2021 18:19:26 GMT
Keep-Alive: timeout=20
Connection: keep-alive


# A relative path for restaurants controller: "/rest/restaurants"

## Check restaurants with their current menu:

curl --location --request GET 'http://localhost:8080/rest/restaurants' \
--header 'Authorization: Basic ZXVnZW5lQG1haWwuY29tOnBhc3N3b3Jk'

## Response Headers

Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 15 Jan 2021 16:54:51 GMT
Keep-Alive: timeout=20
Connection: keep-alive
Response Body

[{"id":10020,"name":"Гавана","menu":{"Винегрет":7000,"Беляш":10000,"Бигос":12000,"Бисквит":5000}}, 

{"id":10022,"name":"Гараж","menu":{"Стью":12500,"Тарэ":35000,"Токана":6000,"Слатур":11500}},

{"id":10023,"name":"Пляж","menu":{"Шашлык":33000,"Шницель":30500,"Антрекот":13500,"Стейк":45000}},

{"id":10021,"name":"Триполи","menu":{"Брезаола":20000,"Сочник":31000,"Лагман":20000,"Банановый торт":15000}}]

## Check restaurant and last available menu with average rank
curl --location --request GET 'http://localhost:8080/rest/restaurants/10020' \
--header 'Authorization: Basic ZXVnZW5lQG1haWwuY29tOnBhc3N3b3Jk'

## Get all your votes as an authorized user

curl --location --request GET 'http://localhost:8080/rest/votes/' \
--header 'Authorization: Basic ZnJhbmtAbWFpbC5jb206cGFzc3dvcmQ='

## Leave vote for a restaurant:

curl --location --request POST 'http://localhost:8080/rest/votes/10020/4' \
--header 'Authorization: Basic ZnJhbmtAbWFpbC5jb206cGFzc3dvcmQ='

Explanation:
http://localhost:8080/rest/votes/{restaurant id}/{restaurant rank}

Response Headers
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 15 Jan 2021 16:32:03 GMT
Keep-Alive: timeout=20
Connection: keep-alive
Response Body
{
"id":10036,"rating":4,
"date":"2021-01-15 16:32",
"restaurant":{"id":10020}
}

## Delete your voce by id as authorized user:
curl --location --request DELETE 'http://localhost:8080/rest/votes/10053' \
--header 'Authorization: Basic ZnJhbmtAbWFpbC5jb206cGFzc3dvcmQ='

## Update your voce by id as authorized user (before 11AM same day):
curl --location --request PUT 'http://localhost:8080/rest/votes/10027' \
--header 'Authorization: Basic ZnJhbmtAbWFpbC5jb206cGFzc3dvcmQ=' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 10027,
"rating": 0,
"restaurant": {
"id": 10023,
"name": "Пляж"
}
}'

# A registration example

## Request
curl --location --request POST 'http://localhost:8080/rest/profile/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "alexey",
"email": "alexey@mail.com",
"password": "password"
}'

## Response
HTTP/1.1 201 Created  
Location: http://localhost:8080/rest/profile  
Cache-Control: no-cache, no-store, max-age=0, must-revalidate  
Pragma: no-cache  
Expires: 0  
X-XSS-Protection: 1; mode=block  
X-Frame-Options: DENY  
X-Content-Type-Options: nosniff  
Content-Type: application/json  
Transfer-Encoding: chunked  
Date: Tue, 05 Jan 2021 08:57:42 GMT  
Keep-Alive: timeout=20  
Connection: keep-alive  
{"id":10055,"name":"alexey","email":"alexey@mail.com","password":"{bcrypt}$2a$10$j20sFWSjSnaGggCscYIv0O7D6JdAMzT6jkFKPyhb1zI4ZkKMrtg/y","enabled":true,"registered":"2021-01-05 11:57","roles":["USER"]}
