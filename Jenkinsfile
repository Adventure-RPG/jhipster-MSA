pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build apps') {
      steps {
        sh 'jhipster import-jdl apps.jh --from-cli=false --skip-insight --no-insight --with-entities'
        sh 'chmod +x cicd.exp docker.exp'
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
              sh 'cp ../cicd.exp . && ./cicd.exp'
              sh 'sed -e \'s/-u jhipster //g\' -e \'s/gradlew jib/gradlew jib -Djib.allowInsecureRegistries=true --image=borschregistry:5000\\/adventurecore:latest/g\' Jenkinsfile > Jenkinsfile'
            }

          }
        }

        stage('adventureUAA: build JenkinsFile') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureUAA') {
              sh 'cp ../cicd.exp . && ./cicd.exp'
              sh 'sed -e \'s/-u jhipster //g\' -e \'s/gradlew jib/gradlew jib -Djib.allowInsecureRegistries=true --image=borschregistry:5000\\/adventureuaa:latest/g\' Jenkinsfile > Jenkinsfile'
            }

          }
        }

        stage('adventureGateway: build JenkinsFile') {
          options {
            skipDefaultCheckout(true)
          }
          steps {
            dir(path: 'adventureGateway') {
              sh 'cp ../cicd.exp . && ./cicd.exp'
              sh 'sed -e \'s/-u jhipster //g\' -e \'s/gradlew jib/gradlew jib -Djib.allowInsecureRegistries=true --image=borschregistry:5000\\/adventuregateway:latest/g\' Jenkinsfile > Jenkinsfile'
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
                sh '''rm -rf .git/
git init
git remote add origin git@github.com:Adventure-RPG/adventure-core.git
git add .
git commit -m \'#$BUILD_NUMBER\'
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
                sh '''rm -rf .git/
git init
git remote add origin git@github.com:Adventure-RPG/adventure-uaa.git
git add .
git commit -m \'#$BUILD_NUMBER\'
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
                sh '''rm -rf .git/
git init
git remote add origin git@github.com:Adventure-RPG/adventure-gateway.git
git add .
git commit -m \'#$BUILD_NUMBER\'
git push --force origin master'''
              }

            }

          }
        }

      }
    }

    stage('build stack') {
      steps {
        sh 'mkdir docker-compose && cp docker.exp docker-compose/'
        dir(path: 'docker-compose') {
          sh './docker.exp'
          sh '''docker-compose config > docker-compose.tmp.yml
head -n -4 docker-compose.tmp.yml > docker-compose.stack.yml
echo "    configs:
      - source: jhipster_registry_config
        target: /central-config/application.yml
        uid: \'1000\'
        gid: \'1000\'
        mode: 0440
configs:
    jhipster_registry_config:
        file: ./central-server-config/application.yml
version: \'3.7\'" >> docker-compose.stack.yml'''
          sh 'docker stack rm Adventure'
          sh 'docker stack deploy --compose-file docker-compose.stack.yml Adventure'
        }

      }
    }

  }
}