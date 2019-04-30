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
      options {
        skipDefaultCheckout(true)
      }
      steps {
        dir(path: 'adventureCore') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

        dir(path: 'adventureUAA') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

        dir(path: 'adventureGateway') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

      }
    }
  }
}