#!/bin/bash

# Service name
NAME=authen

# Service Jar
JAR_FILE=/target/authentication-management-service.jar


start() {
 echo "Starting $NAME..."
 sudo su
 mkdir $NAME
 cd $NAME
 git pull https://github.com/dungnche140981/authentication-service.git
 cd authentication-service
 mvn clean install
 sudo java -Dserver.port=80 -jar $JAR_FILE > /dev/null 2> /dev/null < /dev/null &
 echo "$NAME started successfully"
}

stop() {
 echo "Stopping $NAME..."
 pid=$(ps -ef | grep $JAR_FILE | grep -v grep | awk '{print $2}')
 kill -9 $pid
 echo "$NAME stopped successfully"
}

case "$1" in
 start)
   start
   ;;
 stop)
   stop
   ;;
 restart)
   stop
   start
   ;;
 *)
   echo "Usage: $0 {start|stop|restart}"
   exit 1
   ;;
esac
