#!/bin/bash

touch deploy.log

/home/ubuntu/deploy.sh > deploy.logl 2> deploy.log < /dev/null &
