# codefellowship

## What is the app? 
CodeFellowship App allows us to learn more about Spring Security,
So it is to hold our work for the last 5 Spring labs. 

## App Routes:

1. `http://localhost:8080/` ->   
   will send you to **Home** page.

2. `http://localhost:8080/signup` -> 
   will send you to **Signup** page.

3. `http://localhost:8080/login` -> 
   will send you to **Login** page.

4. `http://localhost:8080/users/{id}` And `http://localhost:8080/profile` ->   
 will send you to **Profile** page.

5. `http://localhost:8080/posts` -> 
   will send you to **Post Form** page.


## More Information:

1. The dependencies needed:

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'org.postgresql:postgresql'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
}
```
  
2. When the dependencies on postgres and JPA are added, The app will stop working until we have the configuration
   in `application.properties` to access your Postgres database.
     
```
spring.datasource.port= `PORT Number`
spring.datasource.url=jdbc:postgresql://localhost:5432/`dbname`
spring.datasource.username=`username`
spring.datasource.password=`password`
spring.jpa.database=POSTGRESQL
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect
server.error.whitelabel.enabled=false
spring.sql.init.platform=postgres
```     

3. Error Page, steps:
- `server.error.whitelabel.enabled=false` ->
  Adding this the `application.properties` file,
  will disable the error page and show a concise page that originates from the underlying application container.
  
- Create a custom HTML error page, `error.html`. 
  If we save this file in resources/templates directory,
  it'll automatically be picked up by the default Spring Boot's BasicErrorController.
  
- Create a custom ErrorController.