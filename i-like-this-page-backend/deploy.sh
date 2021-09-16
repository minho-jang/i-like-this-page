#!/bin/bash

touch deploy.log

echo "> Checking pid of the running application..." > deploy.log

CURRENT_PID=$(pgrep -f i-like-this-page)

echo "PID = $CURRENT_PID" > deploy.log

if [ -z $CURRENT_PID ]; then
    echo "> There is no running application." > deploy.log
else
    echo "> kill -15 $CURRENT_PID" > deploy.log
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploying a new application..." > deploy.log

echo "> Copy jar files" > deploy.log

cp build/libs/*.jar ../jar/

JAR_NAME=$(ls -tr ../jar/ | grep 'i-like-this-page' | tail -n 1)

echo "> JAR Name: $JAR_NAME" > deploy.log

nohup java -jar ../jar/$JAR_NAME &
