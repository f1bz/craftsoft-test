# Test project for Craftsoft from Andrew Ruban

### Technologies used:
1. Spring Boot 2.3.3 
2. Spring DATA JPA 2.3.3
3. PostgreSQL 11.0

SQL technology was used as there is no need at all to store our structured data at NO-SQL databases.

PostgreSQL was selected as it provides much more different functions that can be used in the future. 
Generally speaking, it is much more flexible than other SQL databases in terms of this test project. 

Spring DATA JPA was used as it is much easier and faster to develop the application with all auto-configured stuff and easy-to-use repositories.

### To run and test the API, follow this instruction:
1. Install docker and docker compose 
2. Run command ```sudo docker-compose -f setup/docker-postgres.yml up -d```
3. Connect to database and execute ```setup/schema.sql```
4. Package project with maven
5. Create config folder and place there filled applications.properties
6. Copy filled applications.properties to target/
7. Run ```sudo docker build -f .Dockerfile .```
8. Run ```sudo docker run -d <image_name> -p:8080:8080```
9. Visit http://localhost:8080/swagger-ui.html to see available API
10. Upload file cdr.txt that contains test data via ```/upload``` endpoint 
11. Try out other endpoints listed in Swagger


### Made assumptions
1. At some points it might be better to use Spring jdbc template that provides more flexibility when executing SQL requests, however it adds some complexity and slow down development process


## Any other considerations/future enhancements
1. Add some checking mechanism for not allowing parallel phone calls from one account at same time
2. Add more filtering parameters like min\max of cost, etc.
3. Add account\destination validating by pattern 
4. Redo calculating average metrics to be calculated on database level

---
Visit deployed application at http://144.91.73.200:8080/swagger-ui.html