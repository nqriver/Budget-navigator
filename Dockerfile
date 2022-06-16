FROM openjdk:11
LABEL maintainer="nqriver"
VOLUME /tmp
ADD ./target/*jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]