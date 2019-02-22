FROM openjdk:latest
MAINTAINER pitong pitong-z@outlook.com

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
COPY comment-srv-1.0.0-SNAPSHOT.jar /comment-service.jar

ENTRYPOINT ["java", "-jar", "/comment-service.jar"]
