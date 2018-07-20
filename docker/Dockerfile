# Build the final Image only with JRE and built App Jar
FROM docker.obc.ov.otto.de/base/openjdk:8-jre-alpine
MAINTAINER Joerg Gellien <joerg.gellien@otto.de>

# set date
RUN apk update && apk upgrade && \
  apk add tzdata net-tools && \
	cp /usr/share/zoneinfo/Europe/Berlin /etc/localtime && \
	echo "Europe/Berlin" >  /etc/timezone && \
	apk del tzdata
# set date

# service port from outside is important if we want to use eureka one time, then port mapping has to be 1:1
ENV PROFILE default
ENV TZ CET
ENV SECURITY_JWT_SECRET=""

# create user with defined user-id and set rights to volume for user
RUN adduser -D -u 1000 app && echo "app:ppa" | chpasswd

COPY ./docker/run.sh /run.sh
COPY ./build/libs/*.jar /app/app.jar

RUN chmod 700 /*.sh && \
	mkdir -p /app && chown -R app:app /app && \
	chown -R app:app /run.sh && \
	chmod +x /run.sh

# run rest of container with user app
WORKDIR /app
USER app

ENTRYPOINT /run.sh $PROFILE $JAVA_HEAP
