pipeline {
  agent any

  options {
    timestamps()
    ansiColor('xterm')
  }

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
      steps {
        checkout scm
      }
    }

    stage('Start Selenium Grid') {
      steps {
        sh '''
          set -e

          # garante que não sobra nada da run anterior
          docker-compose down || true

          docker-compose up -d
          docker ps

          echo "Aguardando Selenium Hub ficar pronto..."
          HUB_ID="$(docker ps -q --filter "name=selenium-hub" | head -n 1)"

          if [ -z "$HUB_ID" ]; then
            echo "Nao achei container do selenium-hub!"
            docker ps
            docker-compose logs --no-color || true
            exit 1
          fi

          for i in $(seq 1 60); do
            STATUS="$(docker exec "$HUB_ID" sh -lc 'curl -s http://localhost:4444/status || true' | tr -d '\\n')"

            # aceita "ready":true e "ready": true e também quando vem dentro de "value"
            if echo "$STATUS" | grep -Eq '"ready"[[:space:]]*:[[:space:]]*true'; then
              echo "Selenium Hub READY!"
              break
            fi

            sleep 2

            if [ "$i" = "60" ]; then
              echo "Timeout esperando o Selenium Hub."
              echo "Ultimo status: $STATUS"
              docker ps
              docker-compose logs --no-color || true
              exit 1
            fi
          done

          echo "Grid OK."
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

          tar -czf - . | docker run --rm -i \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -lc '
              set -e
              mkdir -p /work && cd /work
              tar -xzf -

              # Se seu projeto lê BROWSER/HEADLESS via env, já está ok.
              # Rodando por tags:
              mvn -q test -Dcucumber.filter.tags="${CUCUMBER_TAGS}"

              # Alternativa (se quiser rodar um runner específico):
              # mvn -q -Dtest=WebRunnerTest test
            '
        '''
      }
    }
  }

  post {
    always {
      sh '''
        docker-compose down || true
      '''
    }
  }
}