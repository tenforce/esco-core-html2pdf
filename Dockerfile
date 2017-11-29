FROM maven:3-jdk-7-alpine
MAINTAINER Cecile Tonglet <cecile.tonglet@tenforce.com>

ENV SPRING_PROFILES_ACTIVE test

WORKDIR /app

#ADD pom.xml /app/
#RUN mvn dependency:get

ADD . /app
RUN mvn package

EXPOSE 8080

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar /app/target/convert2file-1.0-SNAPSHOT.war --spring.profiles.active=$SPRING_PROFILES_ACTIVE"]
