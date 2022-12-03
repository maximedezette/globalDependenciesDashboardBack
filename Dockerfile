FROM openjdk:18-jdk-alpine
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]