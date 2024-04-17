#!/bin/bash

git pull

./gradlew clean build -x test

docker-compose down
docker-compose up -d --build
