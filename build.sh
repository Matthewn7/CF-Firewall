#!/bin/bash

# maven-build
# Matthew Hogan

JAR_NAME="cffirewall-1.0-SNAPSHOT.jar"

mvn clean install
clear
mkdir bin
cp target/$JAR_NAME bin/
java -jar target/$JAR_NAME