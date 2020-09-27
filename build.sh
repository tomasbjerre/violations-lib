#!/bin/bash
#curl https://raw.githubusercontent.com/spotbugs/spotbugs/master/spotbugs/etc/messages.xml > src/main/resources/findbugs/messages.xml
#curl https://raw.githubusercontent.com/find-sec-bugs/find-sec-bugs/master/findsecbugs-plugin/src/main/resources/metadata/messages.xml > src/main/resources/findbugs/fsb-messages.xml
./gradlew cE eclipse clean gitChangelogTask build -Dhttp.socketTimeout=60000 -Dhttp.connectionTimeout=60000
