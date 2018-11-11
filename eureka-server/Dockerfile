FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh wait-for-it.sh
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
ENTRYPOINT ["wait-for-it.sh","config-server:9000", "-jar","/app.jar"]