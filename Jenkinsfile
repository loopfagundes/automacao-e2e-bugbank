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
          # Pasta para o cache do Maven no Jenkins
          mkdir -p ${HOME}/.m2/repository

          tar -czf - . | docker run --rm -i \
            -v "${HOME}/.m2/repository:/root/.m2/repository" \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -c "
              mkdir -p /work && cd /work > /dev/null
              tar -xzf - > /dev/null
              mvn clean test -Dcucumber.filter.tags='${CUCUMBER_TAGS}' 1>&2 || true
              tar -czf - allure-results target/allure-results 2>/dev/null || true
            " > results.tar.gz

          if [ -s results.tar.gz ]; then
            tar -xzf results.tar.gz
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