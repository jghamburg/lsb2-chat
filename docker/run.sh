#!/bin/sh
profile="$1"
java_heap="${2:-768m}"
debug_port="${DEBUG_PORT:49999}"

echo "startup the container! find log in docker logs"
if [ -z ${JAVA_REMOTE_DEBUGGING+x} ]; then
  echo "starting container without debugging options"
  java -Djava.security.egd=file:/dev/./urandom \
    -server -Xms${java_heap} -Xmx${java_heap} \
    -Dspring.profiles.active=${profile} -jar /app/app.jar
else
  echo "starting container, internal debug port is 4242"
  java -Djava.security.egd=file:/dev/./urandom \
    -server -Xms${java_heap} -Xmx${java_heap} \
    -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=${debug_port},suspend=n \
    -Dspring.profiles.active=${profile} -jar /app/app.jar
fi
# The End