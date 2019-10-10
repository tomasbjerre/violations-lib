#!/bin/bash
./gradlew cE eclipse clean gitChangelogTask build -Dhttp.socketTimeout=60000 -Dhttp.connectionTimeout=60000
