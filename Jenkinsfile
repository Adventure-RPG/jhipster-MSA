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
      steps {
        dir(path: 'adventureCore') {
          sh './gradlew -Pprod bootWar jibDockerBuild'
        }

      }
    }
    stage('build JenkinsFile') {
      steps {
        dir(path: 'adventureCore') {
          sh 'jhipster ci-cd --autoconfigure-jenkins=true'
        }

      }
    }
    stage('commit new version') {
      steps {
        dir(path: 'adventureCore') {
          git(url: 'https://github.com/Adventure-RPG/adventure-core', branch: 'master', credentialsId: 'ef610b83-b3cf-4209-b051-287bc4531d59', changelog: true)
          sh 'git push origin master --force'
        }

      }
    }
  }
}