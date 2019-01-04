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
    stage('commit') {
      options {
        skipDefaultCheckout(true)
      }
      parallel {
        stage('adventureCore: commit') {
          steps {
            dir(path: 'adventureCore') {
              sh '''git init
git remote set-url origin git@github.com:Adventure-RPG/adventure-core.git
git add .
git commit -am \'new build\'
git push origin master'''
            }

          }
        }
        stage('adventureUAA: commit') {
          steps {
            dir(path: 'adventureUAA') {
              sh '''git init
git remote set-url origin git@github.com:Adventure-RPG/adventure-uaa.git
git add .
git commit -am \'new build\'
git push origin master'''
            }

          }
        }
      }
    }
  }
}