# About the App

This is a simple Java program that connects to an AWS Aurora MySQL database and creates an alias with the value "Hello, World!" When a HTTP GET request is received, the program prints that value.
### Files:
- **HelloWorld.java** - the program
- **ec2_startup.sh** - contains the commands that need to be run on new EC2 instances to ensure the application runs successfully
- **Jenkinsfile** - the instructions for the CI process that the Jenkins server will run.
- **start_server.sh** - starts the application.
- **codedeploy.yaml** - instructions to codedeploy how to use the files in the zip file on S3 to deploy the newly created application.
#
# The Cloud Infrastructure:
### GitHub

- A private GitHub repository (currently public for the purpose of this assignment, but in a real-world scenario it would be private).

### Jenkins Server

- A Jenkins server running as a Docker image on an AWS EC2 instance has aws cli installed on it, and jDK so it can build the artifact and up load it to AWS.

### AWS RDS Database

- An Aurora database running a MySQL database. A small instance has been used for this assignment, but in a production environment a cluster of larger instances would be preferred.

### AWS Auto-Scaling Group

- A group of EC2 instances configured to automatically scale based on traffic.

### AWS S3 Bucket

- Stores the artifacts created by the Jenkins server, and and used on the newly created EC2 instances.

#
#
# Pipeline:

The code goes through a pipeline with the following steps:

1. The developer creates a new commit and pushes it to the remote GitHub repository.
2. The new commit triggers a GitHub webhook that sends a ping to the Jenkins server.
3. Jenkins uses the Jenkins file stored in the GitHub repository to run the Jenkins job.
4. Jenkins builds the artifact and uploads it to the AWS S3 bucket.
5. The AWS CodeDeploy feature pulls the artifact and uploads it to a new EC2 instance in the auto-scaling group.
#
#
# Full Description:
### The App:
A simple Java app that goes through the following steps:

1. Connects to an existing RDS MySQL database. The URL, username, and password are hard-coded into the code (note: this is not a best practice. In real-world applications, these values should be passed as environment variables on the server running the app).
2. Creates a new table in the database.
3. Inserts a new row into the table with an ID of "1" and a message of "Hello World!"
4. Retrieves the "Hello World" message from the database.
5. Starts the HTTP server and prints out the message when there is an HTTP GET request.

I must admit that I am not proficient in the Java language, but this was a very enjoyable task.
### The EC2 Running the App:
- Has Java version 8 installed.
- Has the MySQL connector package installed.
### The Jenkins Job:
The Jenkinsfile contains the following stages:

- Builds the artifact.
- Calculates the new tag of the new commit and pushes it to the repository.
- zips all the necessary files for deployment.
- Pushes the zip file to the AWS S3 bucket.
- sends an email to the developer with the status of the build (success or failure).

### The Auto-Scaling Group:
- The Auto-Scaling Group is configured to mininun 3 instances, and maximum 20 instances.
- there is a Scaling policie that resizes the amount of instances according to the avrage CPU utilization.
- there is a helth check that the load balancer does. if he didects that an instance is not helthy, it will terminate it and start a new one.
- notifications are send out whenever Amazon EC2 Auto Scaling launches or terminates the EC2 instances in the Auto Scaling group.


To start, I created one EC2 instance and installed Java and the MySQL connector on it. I then ran the application on it, checked that it works, and created an AMI image from that instance. I then used the instance as the instance that will be used in the auto scaling group.

### CodeDeploy:
The code deploy agent detects that a new zip file has been replaced in the S3 bucket and starts a new deployment on the auto scaling group using the files from the new zip archive.




#
#
# Security and availability issues:
There are several security and availability measures that can be implemented in the project:

- Implement strong passwords for the database, GitHub, and Jenkins.
- Only allow access to a whitelist of IP addresses.
- Use a private Git repository. The current one is public for easy viewing only.
- Do not allow public access to the database. There is no public IP address assigned to the cluster. Only Amazon EC2 instances and other resources inside the VPC can connect to the cluster.
- Consider creating an Aurora Replica or using a different availability zone (AZ) for increased availability.
- Consider using a larger instance class.
- Enable deletion protection to prevent the database from being deleted accidentally.
- Secure the S3 bucket by blocking all public access.
- the auto scaling group is deployed on all AZ in the region.

**Note:** The instructions stated to allow access to the application only from the office IP. For this exam, it is okay to use the personal IP address. However, I was unable to find a way to access the application using that method. As a temporary solution, I have decided to allow access from all IP addresses for now.