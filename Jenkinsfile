pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build apps') {
      agent {
        node {
          label 'master'
        }

      }
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
      }
    }
    stage('build war') {
      agent {
        node {
          label 'master'
        }

      }
      steps {
        dir(path: 'adventureCore') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

      }
    }
    stage('build JenkinsFile') {
      agent {
        node {
          label 'master'
        }

      }
      steps {
        sh 'jhipster ci-cd --autoconfigure-jenkins=true'
      }
    }
    stage('commit new version') {
      agent {
        node {
          label 'master'
        }

      }
      steps {
        git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', changelog: true, credentialsId: 'shaper api key')
        sh 'git push origin master --force'
      }
    }
  }
}