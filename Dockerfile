FROM maven:latest AS build

WORKDIR /cab
# Has to be set explictly to find binaries
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

COPY src/ ./src

# Build the application using maven
RUN mvn clean package

CMD ["java", "-jar", "target/cab-1.0-SNAPSHOT.jar"]
