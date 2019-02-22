FROM openjdk:latest
MAINTAINER pitong pitong-z@outlook.com

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
COPY api-gateway-1.0.0-SNAPSHOT.jar /api-gateway.jar

ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]
