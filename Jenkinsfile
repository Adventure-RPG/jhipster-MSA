pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build apps') {
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
      }
    }
    stage('build war') {
      steps {
        dir(path: 'adventureCore') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

      }
    }
    stage('build JenkinsFile') {
      steps {
        sh 'jhipster ci-cd --autoconfigure-jenkins=true'
      }
    }
    stage('commit new version') {
      steps {
        git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', changelog: true, credentialsId: 'shaper api key')
        sh 'git push origin master --force'
      }
    }
  }
}