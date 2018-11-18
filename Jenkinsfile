#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    docker.image('jhipster/jhipster:v5.5.0').inside('-u root') {
        stage('check java') {
            sh "java -version"
        }

    }

    def dockerImage
    stage('build docker') {
    }

    stage('publish docker') { 
        docker.withRegistry('localhost:5000', 'JenkinsDockerRegistry') { 
            dockerImage.push 'latest'
        }
    }
}
