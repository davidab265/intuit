# About the App

This is a simple Java program that connects to an AWS Aurora MySQL database, creates an alias in the database with the value "Hello, World!", and then prints that value to the console.
#
# The Cloud Infrastructure:
### GitHub

- A private GitHub repository (currently public for the purpose of this assignment, but in a real-world scenario it would be private).

### Jenkins Server

- A Jenkins server running as a Docker image on an AWS EC2 instance.

### AWS RDS Database

- An Aurora database running a MySQL database. A small instance has been used for this assignment, but in a production environment a cluster of larger instances would be preferred.

### AWS Auto-Scaling Group

- A group of EC2 instances configured to automatically scale based on traffic.

### AWS S3 Bucket

- Stores the artifacts created by the Jenkins server and used on the newly created EC2 instances.

#

# Pipeline:

The code goes through a pipeline with the following steps:

1. The developer creates a new commit and pushes it to the remote GitHub repository.
2. The new commit triggers a GitHub webhook that sends a ping to the Jenkins server.
3. Jenkins uses the Jenkins file stored in the GitHub repository to run the Jenkins job.
4. Jenkins builds the artifact and uploads it to the AWS S3 bucket.
5. The AWS CodeDeploy feature pulls the artifact and uploads it to a new EC2 instance in the auto-scaling group.