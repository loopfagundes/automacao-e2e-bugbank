pipeline {
  agent {
    docker {
      image 'maven:3.9.9-eclipse-temurin-21'
      args '-v /var/run/docker.sock:/var/run/docker.sock'
    }
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Test') {
      steps {
        sh 'mvn -q -Dtest=WebRunnerTest test'
      }
    }
  }
}