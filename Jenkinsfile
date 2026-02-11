pipeline {
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-21'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('Build & Test') {
            steps {
                sh """
                apt-get update
                apt-get install -y chromium chromium-driver
                mvn clean test -Dheadless=true
                """
            }
        }
    }
}