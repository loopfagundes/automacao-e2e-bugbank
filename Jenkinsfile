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

          # opcional: mata qualquer container que esteja segurando 4444 (se sobrou algo antigo)
          docker ps -q --filter publish=4444 | xargs -r docker rm -f

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

          # limpa outputs antigos do workspace
          rm -rf allure-results reports || true

          CID=$(docker create -i \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -lc '
              set -e
              mkdir -p /work && cd /work
              tar -xzf -
              mvn -q test -Dcucumber.filter.tags="${CUCUMBER_TAGS}"
            ')

          # envia o repo pro container e executa
          tar -czf - . | docker start -i "$CID"

          # copia resultados de volta pro workspace
          docker cp "$CID":/work/allure-results ./allure-results || true
          docker cp "$CID":/work/reports ./reports || true

          # remove container
          docker rm -f "$CID" >/dev/null
        '''
      }
    }
  }

  post {
    always {
      sh '''
        docker-compose -f docker-compose.yml down || true
      '''
    }
  }

  post {
    always {

      archiveArtifacts artifacts: 'allure-results/**,reports/**', allowEmptyArchive: true

      script {
        try {
          allure(results: [[path: 'allure-results']])
        } catch (e) {
          echo "Allure plugin não configurado (CLI ausente). Pulando publish. Erro: ${e}"
        }
      }

      sh '''
        docker-compose -f docker-compose.yml down || true
      '''
    }
  }
}