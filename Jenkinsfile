pipeline {
  agent {
    node {
      label 'adventureNode'
    }

  }
  stages {
    stage('build apps') {
      agent {
        node {
          label 'adventureNode'
        }

      }
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
      }
    }
    stage('build war') {
      parallel {
        stage('adventureCore: build war') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'workspace/jhipster-MSA_master/adventureCore') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'workspace/jhipster-MSA_master/adventureUAA') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureGateway: build war') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'workspace/jhipster-MSA_master/adventureGateway')
            sh './gradlew -Pprod bootWar jibDockerBuild'
          }
        }
      }
    }
    stage('build JenkinsFile') {
      parallel {
        stage('AdventureCore: build JenkinsFile') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/jhipster-MSA_master/adventureCore') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
        stage('adventureUAA: build Jenkins') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/jhipster-MSA_master/adventureUAA') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
        stage('adventureGateway: build Jenkins') {
          agent {
            node {
              label 'adventureNode'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/adventureGateway') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
      }
    }
    stage('push to git') {
      parallel {
        stage('AdventureCore: push to git') {
          agent {
            node {
              label 'adventureNode'
            }

          }
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
          agent {
            node {
              label 'adventureNode'
            }

          }
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
          agent {
            node {
              label 'adventureNode'
            }

          }
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