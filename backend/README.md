# Backend

A Spring Boot application that provides endpoints that fetch truck information from a MySQL database.

### MySQL Database setup

To setup the database follow the steps:
- create a new schema in MySQL;
- run "schema.sql" to create the database tables;
- run "data.sql" to insert some test data;
- configure your connection parameters in "application.yml" file, or use other Spring methods to override them, like
  VM parameters or OS parameters.


### Run

Since this is a maven project to run just call:

mvn ...
