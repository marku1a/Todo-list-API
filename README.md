# Todo-list-API

Welcome to my Todo list REST API using Spring Boot, Spring Data Jpa, MySQL!

This is a simple Todo list api having function to create, read, update and delete tasks.
We have task (name of the task), completed (true or false), date created and date updated.
Task and completed fields are used for interaction while date created/updated are automaticlly set.



To install and run the Todo list api on your local machine follow this steps:
1) Make sure you have Java 17, Maven and MySQL installed 
2) Clone the repository to your PC using the following command: 
git clone https://github.com/your-username/todo-list-api.git
3) Navigate to the project directory in console: cd todo-list-api
4) Open the "application.properties" file(located in the src/main/resources directory)
5) Configure the database connection properties according to your setup:
-Set "spring.datasource.url" to specify the database url
-Set "spring.datasource.username" and "spring.datasource.password" to the appropriate
values for your database authentication
6) Build the project using Maven: mvn clean install
7) Start the API by running the following command: mvn spring-boot:run
(Api should now be running locally at "http://localhost:8080/api/v1/tasks/")
Use API tools like HTTPie/Postman or use curl to make http requests 


