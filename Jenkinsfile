pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Test (Maven via tar)') {
      steps {
        sh '''
          set -e

          # empacota o workspace e envia pro container maven
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
}