#!/bin/bash

NAME="admin"
mvn clean && mvn spring-boot:build-image -Dmaven.test.skip=true
docker tag $NAME-service:0.0.1-SNAPSHOT hiroshinobuoka/$NAME-service
docker image rm $NAME-service:0.0.1-SNAPSHOT
docker push hiroshinobuoka/$NAME-service
