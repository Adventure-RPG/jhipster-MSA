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
          agent {
            node {
              label 'master'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/jhipster-MSA_master/adventureCore') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureUAA: build war') {
          agent {
            node {
              label 'master'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/jhipster-MSA_master/adventureUAA') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
        stage('adventureGateway: build war') {
          agent {
            node {
              label 'master'
            }

          }
          options {
            skipDefaultCheckout(true)
          }
          steps {
            ws(dir: 'workspace/jhipster-MSA_master/adventureGateway') {
              sh './gradlew -Pprod bootWar jibDockerBuild'
            }

          }
        }
      }
    }
    stage('build JenkinsFile') {
      parallel {
        stage('AdventureCore: build JenkinsFile') {
          agent {
            node {
              label 'master'
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
              label 'master'
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
              label 'master'
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
              label 'master'
            }

          }
          steps {
            ws(dir: 'workspace/adventureCore') {
              git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', changelog: true, credentialsId: 'adventure_main_rsa', poll: true)
            }

          }
        }
        stage('adventureUAA: push to git') {
          steps {
            ws(dir: 'workspace/adventureUAA') {
              git(url: 'https://github.com/Adventure-RPG/adventure-uaa', branch: 'master', changelog: true, credentialsId: 'adventure_main_rsa', poll: true)
            }

          }
        }
        stage('adventureGateway: push to git') {
          steps {
            ws(dir: 'workspace/adventureGateway') {
              git(url: 'https://github.com/Adventure-RPG/adventure-gateway', branch: 'master', changelog: true, credentialsId: 'adventure_main_rsa')
            }

          }
        }
      }
    }
  }
}