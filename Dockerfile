FROM adoptopenjdk/openjdk11

WORKDIR /current

COPY build/libs/todo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]