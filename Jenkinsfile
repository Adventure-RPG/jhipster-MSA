pipeline {
  agent {
    node {
      label 'build apps'
    }

  }
  stages {
    stage('build apps') {
      agent {
        node {
          label 'build apps step'
        }

      }
      steps {
        sh 'jhipster import-jdl apps.jh'
      }
    }
    stage('build war') {
      agent {
        node {
          label 'build war step'
        }

      }
      steps {
        sh '''cd adventureCore
./gradlew -Pprod bootWar jibDockerBuild
'''
      }
    }
    stage('build JenkinsFile') {
      agent {
        node {
          label 'build jenkinsfile'
        }

      }
      steps {
        sh 'jhipster ci-cd --autoconfigure-jenkins=true'
      }
    }
    stage('commit new version') {
      agent {
        node {
          label 'commit new version step'
        }

      }
      steps {
        git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', changelog: true, credentialsId: 'shaper api key')
        sh 'git push origin master --force'
      }
    }
  }
}