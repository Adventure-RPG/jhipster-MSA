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
      parallel {
        stage('adventureCore: commit') {
          steps {
            dir(path: 'adventureCore') {
              git(url: 'https://shaper2111:df9572fc005e6335dac6119a8b12b9bdeedc63a3@github.com/Adventure-RPG/adventure-core.git', branch: 'master', credentialsId: 'shaper_access_key', changelog: true)
              sh 'echo $GIT_SSH'
              sh 'git push origin master --force'
            }

          }
        }
        stage('adventureUAA: commit') {
          steps {
            dir(path: 'adventureUAA') {
              git(url: 'https://shaper2111:df9572fc005e6335dac6119a8b12b9bdeedc63a3@github.com/Adventure-RPG/adventure-uaa.git', branch: 'master', changelog: true, credentialsId: 'shaper_access_key')
              sh 'echo $GIT_SSH'
              sh 'git push origin master --force'
            }

          }
        }
      }
    }
  }
}