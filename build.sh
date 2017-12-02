#!/bin/bash
./gradlew clean gitChangelogTask build -i --refresh-dependencies -Dhttp.socketTimeout=60000 -Dhttp.connectionTimeout=60000
