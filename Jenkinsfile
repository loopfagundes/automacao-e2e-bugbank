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

          echo "Params:"
          echo "  BROWSER=${BROWSER}"
          echo "  HEADLESS=${HEADLESS}"
          echo "  CUCUMBER_TAGS=${CUCUMBER_TAGS}"

          # garante que a pasta exista no WORKSPACE do Jenkins
          rm -rf allure-results || true
          mkdir -p allure-results

          docker run --rm \
            -u 0:0 \
            -v "$PWD:/work" -w /work \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -lc '
              set -e
              mvn -q clean test \
                -Dcucumber.filter.tags="${CUCUMBER_TAGS}" \
                -Dallure.results.directory=allure-results
            '

          echo "DEBUG (host/workspace):"
          ls -la allure-results || true
        '''
      }
    }

    stage('Reports (Allure)') {
      steps {
        script {
          allure([
            includeProperties: false,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'allure-results']]
          ])
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'allure-results/**,allure-report/**,reports/**,target/**', allowEmptyArchive: true

      sh '''
        docker-compose -f docker-compose.yml down -v --remove-orphans || true
      '''
    }
  }
}