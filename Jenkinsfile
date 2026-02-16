pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Test (Maven container)') {
      steps {
        sh '''
          docker run --rm \
            -v "$PWD":/work \
            -w /work \
            maven:3.9.9-eclipse-temurin-21 \
            mvn -q -Dtest=WebRunnerTest test
        '''
      }
    }
  }
}
