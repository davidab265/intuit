# about the app
This is a simple javascript that connects to an AWS aurora MySql database, creates an alias in the database with the value "Hello, World!", and then prints that value to the console.

# pipeline:
creating a new commit and pushing it to GitHub, triggers a webhook that triggers a Jenkins job. 
Jenkins will build the artifact and upload it to AWS s3.
that will trigger AWS CodeDeploy, which will update the existing autoscaling group.