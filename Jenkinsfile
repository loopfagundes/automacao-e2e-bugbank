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

          echo "DEBUG: workspace root:"
          pwd
          ls -la

          echo "DEBUG: procurando pom.xml..."
          POM_PATH=$(find . -maxdepth 4 -name pom.xml | head -n 1 || true)

          if [ -z "$POM_PATH" ]; then
            echo "ERRO: não achei pom.xml no workspace (até 4 níveis)."
            exit 1
          fi

          PROJECT_DIR=$(dirname "$POM_PATH")
          echo "OK: pom.xml encontrado em: $POM_PATH"
          echo "OK: PROJECT_DIR: $PROJECT_DIR"

          rm -rf allure-results || true
          mkdir -p allure-results

          docker run --rm \
            -u 0:0 \
            -v "$PWD:/work" -w /work \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            -e PROJECT_DIR="$PROJECT_DIR" \
            ${MAVEN_IMAGE} \
            bash -lc '
              set -e
              cd "/work/${PROJECT_DIR}"
              echo "Dentro do container, rodando em: $(pwd)"
              ls -la

              mvn -q clean test \
                -Dcucumber.filter.tags="${CUCUMBER_TAGS}" \
                -Dallure.results.directory=/work/allure-results
            '

          echo "DEBUG: allure-results no workspace:"
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