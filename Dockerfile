FROM bellsoft/liberica-openjdk-alpine AS builder
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build
COPY . .
RUN ./gradlew build

FROM bellsoft/liberica-openjdk-alpine:latest
ENV ARTIFACT_NAME=pixel-battle-dsr-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/build/libs/$ARTIFACT_NAME .
CMD  java -jar $ARTIFACT_NAME