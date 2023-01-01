pipeline {
    agent any    
    options {
        timestamps()
        timeout(time:15, unit:'MINUTES')
    }
    environment {
        AWS_ACCOUNT_ID="232413634925"
        AWS_REGION="ew-central-1"
        APP_FILE="app.tar"
        S3_URL="s3://intuit-3/"

    }

    stages {

        // build the program.
        stage('build the program') {
            steps {
                // Clean before build
                cleanWs()
                // We need to explicitly checkout from SCM here
                checkout scm
                sh "javac -classpath /usr/share/java/mysql-connector-j-8.0.31.jar HelloWorld.java" // build


            }
        }

        // update the correct release version. will be used to tag the artifact and the corrent git commit
        stage('[2] Update Tag') {
            when { branch 'master' }
            steps {
                script{
                    sshagent(credentials: ['friday-1']) {
                        //sh "git fetch --all --tags" // not shure that this is needed, pulling the code pulles the tags as well
                        CURRENT_TAG = sh(script: "git tag | sort -V | tail -1", returnStdout: true)
                        if (CURRENT_TAG.isEmpty() ) {
                            NEW_TAG =  "1.0.0"
                        } 
                        else {
                            (major, minor, patch) = CURRENT_TAG.tokenize(".")
                            patch = patch.toInteger() + 1
                            NEW_TAG = "${major}.${minor}.${patch}"

                        }
                        // add the new tag to corrent commit
                        sh "git tag ${NEW_TAG}"
                        sh "git push --tags"

                    }
                }
            }
        }
                

        stage('[3] upload the artifact to AWS S3') {
           when { branch 'master' }
           steps {
               script {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                       sh "tar -cvf ${APP_FILE} HelloWorld.class AppSpec.yaml ec2_startup.sh start_server.sh" // zip all files that are used by codeeploy.
                       sh "aws s3 cp ${APP_FILE} ${S3_URL}" // upload the archive to S3 bucket

                   }
               }
           }
        }           

    }
    post {
        // cleaning up the system
        always{
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])

        }
        success { 
            emailext body: " jenkins job [${JOB_NAME}](jobname/branch) hes succeeded! the new build is taged: ${NEW_TAG} . the results of the build can be found heer: ${BUILD_URL} ", recipientProviders: [buildUser()], subject: " jenkins ${JOB_NAME} success. new build tag: ${NEW_TAG} ", to: '9200200@gmail.com'  
        }  
        failure {  
            emailext body: " jenkins [${JOB_NAME}](jobname/branch) failure. no new build was executed. the current build is: " , recipientProviders: [buildUser()], subject:  " jenkins ${JOB_NAME} failed :( ", to: '9200200@gmail.com';  
        }  
    }
}


