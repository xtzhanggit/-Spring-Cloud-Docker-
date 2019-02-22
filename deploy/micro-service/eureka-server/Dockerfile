FROM openjdk:latest
MAINTAINER pitong pitong-z@outlook.com

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
COPY eureka-server-1.0.0-SNAPSHOT.jar /eureka-server.jar

ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
