FROM openjdk:8
FROM maven:alpine
EXPOSE 8080
ADD ./target/mightyBlockGram.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]