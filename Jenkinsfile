pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Debug workspace') {
      steps {
        sh '''
          echo "PWD:" && pwd
          echo "LIST ROOT:" && ls -la
          echo "FIND POM:" && find . -maxdepth 4 -name pom.xml -print
        '''
      }
    }

    stage('Test (Maven container)') {
      steps {
        sh '''
          docker run --rm \
            -v "$PWD":/work \
            -w /work \
            maven:3.9.9-eclipse-temurin-21 \
            sh -lc "ls -la && find . -maxdepth 4 -name pom.xml -print"
        '''
      }
    }
  }
}