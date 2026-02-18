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
              mvn -q test -Dcucumber.filter.tags="${CUCUMBER_TAGS}"
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