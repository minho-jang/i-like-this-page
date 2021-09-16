#!/bin/bash

touch /home/ubuntu/deploy.log

/home/ubuntu/deploy.sh > deploy.log 2> deploy.log < /dev/null &
