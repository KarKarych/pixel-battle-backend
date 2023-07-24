FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/pixel-battle-dsr-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
