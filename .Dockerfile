FROM openjdk:8-jre-alpine
COPY target/core-*.jar /app.jar
COPY target/*.properties /config/application.properties
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "/app.jar"]
