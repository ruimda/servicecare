# Truck-Service

This service, a Java 11 Spring Boot application, provides endpoints that fetch truck information from a MySQL database.

## Setup

### MySQL Database
To setup the database follow the steps below:

- create a new schema in MySQL;
- run `schema.sql` (under src/main/resources) to create the database tables;
- run `data.sql` to insert some test data;
- configure your connection parameters in `application.yml` file, or use other Spring methods to override them, like OS env variables (you can use `run.bat` which specifies this vars)

### Service setup

Service parameters present in `application.yml`:
- port - http where service runs (set to 8081)
- app.location-history-size - number os locations returns by the locations endpoint (set to 5)

## Build

To build the service just call `mvn package`. It will compile and generate the JAR file under the `target` folder.

## Run

Call `mvn spring-boot:run` to run, or, if you already have the JAR file, call `java -jar target\truck-service-0.0.1-SNAPSHOT.jar`


If all goes well you should see a screen like this:
````
  ______                __        _____                 _                                                                                         
 /_  __/______  _______/ /__     / ___/___  ______   __(_)_______                                                                                 
  / / / ___/ / / / ___/ //_/_____\__ \/ _ \/ ___/ | / / / ___/ _ \                                                                                
 / / / /  / /_/ / /__/ ,< /_____/__/ /  __/ /   | |/ / / /__/  __/                                                                                
/_/ /_/   \__,_/\___/_/|_|     /____/\___/_/    |___/_/\___/\___/                                                                                 
                                                                                                                                                  
By: Rui Anastácio                                                                                                                                 
------------------------------------------------------------------                                                                                
2020-04-06 16:29:23.497  INFO 416 --- [           main] c.r.t.TruckServiceApplication            : Starting TruckServiceApplication v0.0.1-SNAPSHOT ...
...
````



## Test
 
You can check service is UP using health endpoint from Spring Actuator: 

http://localhost:8081/actuator/health

Also included is a collection of Postman requests used in development.

## Unit tests

There are a few tests over the service and controller. This can be executed from the IDE or cmd using maven.

## API Documentation

OpenAPI 3 specification docs are generated automatically, using springdoc-openapi, and can be found (when service is running) at:

[http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

