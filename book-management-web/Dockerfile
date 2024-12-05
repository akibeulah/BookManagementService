FROM openjdk:8-jdk-alpine
EXPOSE 8010
COPY target/*.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]