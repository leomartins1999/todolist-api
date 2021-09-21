FROM openjdk:16-alpine3.13

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle.kts settings.gradle.kts
COPY build.gradle.kts build.gradle.kts
COPY src src

RUN ./gradlew build -x test
