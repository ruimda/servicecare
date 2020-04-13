
set SPRING_DATASOURCE_URL=jdbc:mysql://${MYSQL_HOST:localhost}:3306/trucks?serverTimezone=GMT
set SPRING_DATASOURCE_USERNAME=root
set SPRING_DATASOURCE_PASSWORD=myroot

java -jar target\truck-service-0.0.1-SNAPSHOT.jar