FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE=app.jar
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
