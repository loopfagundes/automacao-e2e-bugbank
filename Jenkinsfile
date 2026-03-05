pipeline {
  agent any

  parameters {
    choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser para rodar no Grid')
    booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Rodar headless?')
    string(name: 'CUCUMBER_TAGS', defaultValue: '@Regressivo', description: 'Tags do Cucumber. Ex: @Regressivo or @Smoke and not @Wip')
  }

  environment {
    MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-21'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Start Selenium Grid') {
      steps {
        sh '''
          set -e
          docker-compose -f docker-compose.yml down || true
          docker ps -q --filter "publish=4444" | xargs -r docker rm -f || true
          docker-compose -f docker-compose.yml up -d
          docker ps
        '''
      }
    }


        stage('Test') {
          agent {
            docker {
              image "${MAVEN_IMAGE}"
              // Isso mapeia automaticamente o workspace e mantém os arquivos
              args '-v /var/run/docker.sock:/var/run/docker.sock'
            }
          }
          steps {
            sh 'mvn clean test -Dcucumber.filter.tags="${CUCUMBER_TAGS}" -DBROWSER="${BROWSER}" -DHEADLESS="${HEADLESS}"'
          }
        }

  }

   post {
       always {
           sh 'docker-compose down || true'
           allure includeProperties: false,
                  jdk: '',
                  results: [[path: 'allure-results'], [path: 'target/allure-results']]
       }
   }

}