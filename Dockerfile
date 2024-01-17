FROM eclipse-temurin:17
COPY build/libs/*.jar app.jar
COPY src/main/resources/static/images /app/static/images
ENTRYPOINT ["java","-jar","/app.jar"]
