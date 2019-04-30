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
      options {
        skipDefaultCheckout(true)
      }
      steps {
        ws(dir: 'jhipster-MSA_master') {
          sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight'
        }

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
            ws(dir: 'jhipster-MSA_master/adventureCore') {
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
            ws(dir: 'jhipster-MSA_master/adventureUAA') {
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
            ws(dir: 'jhipster-MSA_master/adventureGateway') {
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
            ws(dir: 'jhipster-MSA_master/adventureCore') {
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
            ws(dir: 'jhipster-MSA_master/adventureUAA') {
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
            ws(dir: 'adventureGateway') {
              sh 'jhipster ci-cd --autoconfigure-jenkins=true'
            }

          }
        }
      }
    }
  }
}