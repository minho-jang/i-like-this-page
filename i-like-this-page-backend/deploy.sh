#!/bin/bash

echo "> Checking pid of the running application..."

CURRENT_PID=$(pgrep -f i-like-this-page)

echo "PID = $CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> There is no running application."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploying a new application..."

echo "> Copy jar files"

cp build/libs/*.jar ../jar/

JAR_NAME=$(ls -tr ../jar/ | grep 'i-like-this-page' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar ../jar/$JAR_NAME &
