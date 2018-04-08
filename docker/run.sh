#!/bin/sh
profile="$1:-local"
java_heap="${2:-768m}"

echo "startup the container! find log in docker logs"
java -Djava.security.egd=file:/dev/./urandom -server -Xms${java_heap} -Xmx${java_heap} -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4242,suspend=n -Dspring.profiles.active=${profile} -jar /app/app.jar
