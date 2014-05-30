#!/bin/bash

DIR="$( cd "$( dirname "$0" )" && pwd )"
. ${DIR}/env.sh

if [ "$1" = "" ]; then
    echo "Usage: deployToTomcat.sh <script-manager-username> <script-manager-password>"
    echo "  script-manager-username and script-manager-password must be defined in your Tomcat config file tomcat-users.xml, and must have the manager-script role."
    exit
fi

if [ "$2" = "" ]; then
    echo "Usage: deployToTomcat.sh <script-manager-username> <script-manager-password>"
    echo "  script-manager-username and script-manager-password must be defined in your Tomcat config file tomcat-users.xml, and must have the manager-script role."
    exit
fi

mvn tomcat7:deploy -Dtomcat.username=$1 -Dtomcat.password=$2 -Dmaven.tomcat.url=http://${TOMCAT_HOST}:${TOMCAT_PORT}/manager/text -Dmaven.tomcat.update=true

