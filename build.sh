#!/bin/bash

# maven-build
# Matthew Hogan

JAR_NAME="cffirewall-1.0-SNAPSHOT.jar"

mvn clean install
clear
java -jar target/$JAR_NAME