# Build the final Image only with JRE and built App Jar
FROM openjdk:8-jre-alpine
MAINTAINER Joerg Gellien <joerg.gellien@otto.de>

# set date
RUN apk update && apk upgrade && apk add tzdata && apk add net-tools
RUN cp /usr/share/zoneinfo/Europe/Berlin /etc/localtime
RUN echo "Europe/Berlin" >  /etc/timezone
RUN apk del tzdata
# set date

# service port from outside is important if we want to use eureka one time, then port mapping has to be 1:1
ENV PROFILE default
ENV TZ CET
ENV SECURITY_JWT_SECRET=""

# create user with defined user-id and set rights to volume for user
RUN adduser -D -u 1000 app && echo "app:ppa" | chpasswd

COPY ./docker/run.sh /run.sh

RUN chmod 700 /*.sh

RUN mkdir /app && chown -R app:app /app && chown -R app:app /run.sh && chmod +x /run.sh
WORKDIR /app

# run rest of container with user app
USER app

COPY ./build/libs/*.jar /app/app.jar

ENTRYPOINT /run.sh $PROFILE $JAVA_HEAP
