# neotech-task
Console application written on Java that uses java.util.concurrent to effectively write the data to Mongo DB.

## Supported functionality:
 - Write current date to DB every second
 > java -jar target/neotech-1.0.jar
 - Print all data from DB:
 > java -jar target/neotech-1.0.jar -p
 - Reset DB data:
 > java -jar target/neotech-1.0.jar -c

## Technology stack:
 - Maven [3+](https://maven.apache.org/)
 - Java [1.8+](http://www.oracle.com/technetwork/java/javase/overview/index.html)
 - MongoDB [latest](https://www.mongodb.com)
 
## Build the JAR file
 > mvn clean install

## MongoDB
mongodb://localhost:27017