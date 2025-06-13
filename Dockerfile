FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/be-searchPibisi-1.0.1-SNAPSHOT.jar pibisi-1.0.1.jar
ENTRYPOINT ["java","-jar","/pibisi-1.0.1.jar"]