#!/bin/bash

DIR="$( cd "$( dirname "$0" )" && pwd )"
. ${DIR}/env.sh

MAVEN_OPTS=-"javaagent:${APPD_AGENT}/javaagent.jar -Dappdynamics.agent.applicationName=${APP_NAME} -Dappdynamics.agent.tierName=${SERVER_TIER_NAME} -Dappdynamics.agent.nodeName=${SERVER_NODE_NAME}" \
    mvn exec:java -Dexec.mainClass=com.appdynamics.selab.tcpserver.TCPServer -Dexec.arguments=6789
