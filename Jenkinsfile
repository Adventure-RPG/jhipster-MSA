pipeline {
  agent none
  options {
      skipDefaultCheckout true
  }
  stages {
    stage('build apps') {
      agent none
      options {
          skipDefaultCheckout true
      }
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
      }
    }
    stage('build war') {
      parallel {
        stage('adventureCore: build war') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureCore') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureUAA') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureGateway: build war') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureGateway') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
      }
    }
    stage('build JenkinsFile') {
      parallel {
        stage('AdventureCore: build JenkinsFile') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureCore') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

            sh 'sh \'scp -r AdventureCore web@web.iamborsch.ru:projects/\''
          }
        }
        stage('adventureUAA: build Jenkins') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureUAA') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
        stage('adventureGateway: build Jenkins') {
          agent none
          options {
              skipDefaultCheckout true
          }
          steps {
            dir(path: 'adventureGateway') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
      }
    }
  }
}
