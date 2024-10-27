FROM openjdk:21-jdk

WORKDIR /app
COPY core/build/libs/core-main.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]