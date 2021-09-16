#!/bin/bash

touch deploy.log

deploy.sh > deploy.logl 2> deploy.log < /dev/null &
