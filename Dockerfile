FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/agile-mind-api.jar
ADD ${JAR_FILE} agile-mind-api.jar
ENTRYPOINT ["java","-jar","/agile-mind-api.jar"]