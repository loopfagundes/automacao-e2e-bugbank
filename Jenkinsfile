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
      steps {
        sh '''
          set -e
          # Envia o projeto via tar, roda o maven e devolve os resultados via stdout para um arquivo local
          tar -czf - . | docker run --rm -i \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -c '
              mkdir -p /work && cd /work
              tar -xzf -
              mvn clean test -Dcucumber.filter.tags="${CUCUMBER_TAGS}" || true
              tar -czf - allure-results target/allure-results 2>/dev/null || true
            ' > results.tar.gz

          # Extrai os resultados no workspace para o Allure Plugin encontrar
          if [ -f results.tar.gz ]; then
            tar -xzf results.tar.gz
          else
            echo "ERRO: results.tar.gz não foi gerado."
          fi
        '''
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
