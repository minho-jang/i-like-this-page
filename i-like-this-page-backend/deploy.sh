#!/bin/bash

REPOSITORY=/home/ec2-user/app

echo "> Checking pid of the running application..."

CURRENT_PID=$(pgrep -f i-like-this-page)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> There is no running application."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploying a new application..."

echo "> Copy jar files"

cp $REPOSITORY/deploy/build/libs/*.jar $REPOSITORY/jar/

JAR_NAME=$(ls $REPOSITORY/jar/ | grep 'i-like-this-page' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/jar/$JAR_NAME &