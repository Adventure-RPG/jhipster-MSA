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
}
