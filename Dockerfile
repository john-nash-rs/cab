FROM maven:latest AS build

WORKDIR /cab
# Has to be set explictly to find binaries
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

COPY src/ ./src

# Build the application using maven
RUN mvn clean package



# Second stage: minimal runtime environment
From openjdk:8-jre-alpine
WORKDIR /cab

# copy jar from the first stage
COPY --from=build /cab/target/cab-1.0-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "cab-1.0-SNAPSHOT.jar"]
