#!/bin/bash
javac -classpath ../external_jars/selenium-chrome-driver-4.9.0.jar -classpath ../external_jars/selenium-firefox-driver-4.9.0.jar -classpath ../external_jars/selenium-java-4.9.0.jar -classpath ../external_jars/jsoup-1.15.4.jar $1 2>&1 | tee



