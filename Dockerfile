FROM ubuntu:latest
RUN apt-get update
RUN apt-get install openjdk-8-jdk -y
COPY build/libs/email-connector-0.0.1-SNAPSHOT.jar /
CMD ["java","-jar","/email-connector-0.0.1-SNAPSHOT.jar"]
