#! /bin/bash

#============================================================================
# this script is used to install dependencies needed to run the program.
# As of now there is no need to run it, becuse i am using an existing AMI image
# that already has evrything installed on it.
# You should run this script only in case you want to run the program on a new
# installation, someware else in the sky (cloud)...
#
#
# you can copy this script to the 'user data' section in the 'advanced' section
# when creating a new EC2 instance, and it will be installed when the new instance
# is created.
#============================================================================

# install java
sudo add-apt-repository ppa:linuxuprising/java -y
sudo apt update
sudo apt install openjdk-8-jdk -y

# download the mysql connecter package
wget -q https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j_8.0.31-1ubuntu20.04_all.deb

# install the package you just downloaded 
sudo dpkg -i mysql-connector-j_8.0.31-1ubuntu20.04_all.deb 


# run the program
java -classpath /usr/share/java/mysql-connector-j-8.0.31.jar:. HelloWorld







#============================================================================
# some old stuff that i am keeping just in case i will need it
#============================================================================

# install mysql client
#sudo apt install mysql-client-core-8.0 -y

# compile
#javac -classpath /usr/share/java/mysql-connector-j-8.0.31.jar HelloWorld.java

#david9292intuit
