FROM openjdk:17-jdk
ARG JAR_FILE=./build/libs/waktfolio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
