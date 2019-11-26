FROM azul/zulu-openjdk-alpine:11.0.5 as packager

# First stage: JDK 11 with modules required for Spring Boot
RUN /usr/lib/jvm/zulu-11/bin/jlink \
    --module-path /usr/lib/jvm/zulu-11/jmods \
    --verbose \
    --add-modules java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.compiler \
    --compress 2 \
    --no-header-files \
    --output /usr/lib/jvm/zulu-11-minimal
COPY build/libs/*.jar /
ENV JAVA_HOME=/usr/lib/jvm/zulu-11
ENV PATH="$PATH:$JAVA_HOME/bin"

# Second stage, add only our custom jdk11 distro and our app
FROM alpine:3.10
COPY --from=packager /usr/lib/jvm/zulu-11-minimal /opt/jdk-11-minimal
# create user with defined user-id and set rights to volume for user
ENV JAVA_HOME=/opt/jdk-11-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
# app specific environment
ENV PROFILE local
ENV SECURITY_JWT_SECRET=""
ENV JAVA_HEAP=768m
# secure application under separate user
COPY docker/run.sh /app/run.sh
RUN adduser -D -u 1000 app && echo "app:ppa" | chpasswd && \
  chown -R app:app /app && chmod +x /app/run.sh

WORKDIR /app
# run rest of container with user app
USER app
COPY build/libs/*.jar /app/app.jar

ENTRYPOINT /app/run.sh $PROFILE $JAVA_HEAP
