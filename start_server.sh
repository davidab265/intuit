#! /bin/bash


# run the program on port 443
java -Dserver.port=443 -classpath /usr/share/java/mysql-connector-j-8.0.31.jar:. HelloWorld

