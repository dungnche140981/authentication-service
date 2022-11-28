FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} authentication-management-service.jar
ENTRYPOINT ["java","-jar","authentication-management-service.jar"]
