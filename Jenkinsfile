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
      parallel {
        stage('adventureCore: build war') {
          steps {
            dir(path: 'adventureCore') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          steps {
            dir(path: 'adventureUAA') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
      }
    }
    stage('build JenkinsFile') {
      parallel {
        stage('AdventureCore: build JenkinsFile') {
          steps {
            dir(path: 'adventureCore') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
        stage('adventureUAA: build Jenkins') {
          steps {
            dir(path: 'adventureUAA') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
      }
    }
  }
}