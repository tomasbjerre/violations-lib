#!/bin/bash
./gradlew clean gitChangelogTask build -i -Dhttp.socketTimeout=60000 -Dhttp.connectionTimeout=60000
