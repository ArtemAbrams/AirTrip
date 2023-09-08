FROM maven:3.8-amazoncorretto-19 AS constructor
WORKDIR /AirTrip
COPY . /AirTrip/.
RUN mvn -f /AirTrip/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:19
WORKDIR /AirTrip
COPY  --from=constructor /AirTrip/target/AirTrip-0.0.1-SNAPSHOT.jar air_trip-app.jar
ENTRYPOINT ["java", "-jar", "air_trip-app.jar"]