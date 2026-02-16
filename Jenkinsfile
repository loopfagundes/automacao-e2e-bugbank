pipeline {
  agent any

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

    stage('Test (Maven via tar)') {
      steps {
        sh '''
          set -e
          tar -czf - . | docker run --rm -i \
            maven:3.9.9-eclipse-temurin-21 \
            bash -lc '
              mkdir -p /work && cd /work
              tar -xzf -
              mvn -q -Dtest=WebRunnerTest test
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
