pipeline {
  agent any
  stages {
    stage('build apps') {
      agent any
      steps {
        sh 'jhipster import-jdl apps.jh --interactive --from-cli=false'
      }
    }
    stage('build war') {
      agent any
      steps {
        sh '''cd adventureCore
./gradlew -Pprod bootWar jibDockerBuild
'''
      }
    }
    stage('build JenkinsFile') {
      agent any
      steps {
        sh 'jhipster ci-cd --autoconfigure-jenkins=true'
      }
    }
    stage('commit new version') {
      agent any
      steps {
        git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', changelog: true, credentialsId: 'shaper api key')
        sh 'git push origin master --force'
      }
    }
  }
}