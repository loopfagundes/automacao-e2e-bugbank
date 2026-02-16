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
          docker-compose down || true
          docker-compose up -d
          echo "Aguardando Selenium Hub ficar pronto..."
          for i in $(seq 1 60); do
            if docker exec $(docker ps -q --filter "name=selenium-hub") curl -s http://localhost:4444/status | grep -q '"ready":true'; then
              echo "Selenium Hub READY!"
              break
            fi
            sleep 2
            if [ "$i" = "60" ]; then
              echo "Timeout esperando o Selenium Hub."
              docker ps
              docker-compose logs --no-color || true
              exit 1
            fi
          done
          docker ps
        '''
      }
    }

    stage('Test') {
      steps {
        sh '''
          set -e
          tar -czf - . | docker run --rm -i \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -lc '
              mkdir -p /work && cd /work
              tar -xzf -
              mvn -q test -Dcucumber.filter.tags="${CUCUMBER_TAGS}"
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