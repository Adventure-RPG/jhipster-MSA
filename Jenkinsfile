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
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureCore') {
              sh './gradlew -Pprod bootJar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh './gradlew -Pprod bootJar jibDockerBuild'
            }

          }
        }
        stage('adventureGateway: build war') {
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
            sh 'jhipster ci-cd --autoconfigure-jenkins=true'
          }
        }
        stage('adventureUAA: build Jenkins') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            sh 'jhipster ci-cd --autoconfigure-jenkins=true'
          }
        }
        stage('adventureGateway: build Jenkins') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            sh 'jhipster ci-cd --autoconfigure-jenkins=true'
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
            ws(dir: 'workspace/adventureCore') {
              ws(dir: 'workspace/jhipster-MSA_master/adventureCore') {
                sh 'cp -r . ../../adventureCore/'
              }

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
            ws(dir: 'workspace/adventureUAA') {
              ws(dir: 'workspace/jhipster-MSA_master/adventureUAA') {
                sh 'cp -r . ../../adventureUAA/'
              }

              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'adventure_main_rsa', keyFileVariable: 'SSH_KEY')]) {
                sh '''git remote set-url origin git@github.com:Adventure-RPG/adventure-uaa.git
git add .
git commit -m \'#${BUILD_NUMBER}\''''
              }

            }

          }
        }
        stage('adventureGateway: push to git') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/adventureGateway') {
              git(url: 'https://github.com/Adventure-RPG/adventure-gateway', branch: 'master', changelog: true, credentialsId: 'adventure_main_rsa', poll: true)
              ws(dir: 'workspace/jhipster-MSA_master/adventureGateway') {
                sh 'cp -r . ../../adventureGateway/'
              }

              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'adventure_main_rsa', keyFileVariable: 'SSH_KEY')]) {
                sh '''git remote set-url origin git@github.com:Adventure-RPG/adventure-gateway.git
git add .
git commit -m \'#${BUILD_NUMBER}\'
git push origin master'''
              }

            }

          }
        }
      }
    }
  }
}