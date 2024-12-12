FROM gradle:jdk17 AS bulider
WORKDIR /builder

COPY build.gradle settings.gradle gradlew /builder/
COPY ./gradle /builder/gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY ./src /builder/src
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17
WORKDIR /app

COPY --from=bulider /builder/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]