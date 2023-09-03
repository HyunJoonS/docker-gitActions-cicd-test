FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9090

HEALTHCHECK --interval=10s CMD wget -qO- localhost:9090

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev","/app.jar"]

## docker build -t shinhyunjoon/lulla-nft:dev -f Dockerfile .
## docker push shinhyunjoon/lulla-nft:dev
