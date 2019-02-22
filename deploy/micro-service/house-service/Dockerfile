FROM openjdk:latest
MAINTAINER pitong pitong-z@outlook.com

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
COPY house-srv-1.0.0-SNAPSHOT.jar /house-service.jar

ENTRYPOINT ["java", "-jar", "/house-service.jar"]
