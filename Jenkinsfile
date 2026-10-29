pipeline {
    agent any

    tools {
        maven "MAVEN3.9"
        jdk "JDK17"
    }

    environment {
        REGISTRY_CREDENTIALS = 'ecr:us-east-1:awscreds'
        APP_REGISTRY = '243425059953.dkr.ecr.us-east-1.amazonaws.com/vprofileappimg'
        ECR_REGISTRY = 'https://243425059953.dkr.ecr.us-east-1.amazonaws.com'
    }

    stages {
        stage('Fetch code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/aminekhalfaoui343/Jenkins-CICD-Pipline.git'
            }
        }

        stage('Build application') {
            steps {
                sh 'mvn clean package -DskipTests'
                archiveArtifacts artifacts: 'target/*.war'
            }
        }
    

        stage('Build and upload image to ECR') {
            steps {
                script {
                    def image = docker.build(
                        "${APP_REGISTRY}:${BUILD_NUMBER}",
                        '-f Docker-files/app/Dockerfile .')

                    docker.withRegistry(ECR_REGISTRY, REGISTRY_CREDENTIALS) {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }
    }
}
