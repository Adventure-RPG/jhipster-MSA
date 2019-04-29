pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build apps') {
      options {
        skipDefaultCheckout(true)
      }
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
      }
    }
    stage('build war') {
      parallel {
        stage('adventureCore: build war') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureCore') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureGateway: build war') {
          options {
            skipDefaultCheckout(true)
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
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureCore') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

            sh 'sh \'scp -r AdventureCore web@web.iamborsch.ru:projects/\''
          }
        }
        stage('adventureUAA: build Jenkins') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
        stage('adventureGateway: build Jenkins') {
          options {
            skipDefaultCheckout(true)
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
  options {
    skipDefaultCheckout(true)
  }
}