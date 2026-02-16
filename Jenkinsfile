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

          # 1) sempre limpar antes (se já estiver rodando, derruba)
          docker-compose down || true

          # 2) subir o grid
          docker-compose up -d

          # 3) mostrar status
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
        # sempre derruba no final (mesmo se falhar)
        docker-compose down || true
      '''
    }
  }
}
