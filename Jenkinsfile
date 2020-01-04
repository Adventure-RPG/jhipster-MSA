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

    stage('build jar') {
      parallel {
        stage('adventureCore: build jar') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureCore') {
              sh './gradlew -Pprod bootJar jibDockerBuild'
            }

          }
        }

        stage('adventureUAA: build jar') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh './gradlew -Pprod bootJar jibDockerBuild'
            }

          }
        }

        stage('adventureGateway: build jar') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureGateway') {
              sh './gradlew -Pprod bootJar jibDockerBuild'
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

          }
        }

        stage('adventureUAA: build JenkinsFile') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }

        stage('adventureGateway: build JenkinsFile') {
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

    stage('push to git') {
      parallel {
        stage('AdventureCore: push to git') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureCore') {
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'adventure_main_rsa', keyFileVariable: 'SSH_KEY')]) {
                sh '''git remote set-url origin git@github.com:Adventure-RPG/adventure-core.git
git add .
git commit -m \'#${BUILD_NUMBER}\'
git push --force origin master'''
              }

            }

          }
        }

        stage('adventureUAA: push to git') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'adventure_main_rsa', keyFileVariable: 'SSH_KEY')]) {
                sh '''git remote set-url origin git@github.com:Adventure-RPG/adventure-uaa.git
git add .
git commit -m \'#${BUILD_NUMBER}\'
git push --force origin master'''
              }

            }

          }
        }

        stage('adventureGateway: push to git') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureGateway') {
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'adventure_main_rsa', keyFileVariable: 'SSH_KEY')]) {
                sh '''git remote set-url origin git@github.com:Adventure-RPG/adventure-gateway.git
git add .
git commit -m \'#${BUILD_NUMBER}\'
git push --force origin master'''
              }

            }

          }
        }

      }
    }

  }
}