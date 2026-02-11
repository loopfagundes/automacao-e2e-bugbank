pipeline {
    agent {
        docker {
            image 'selenium/standalone-chrome:latest'
            args '--shm-size=2g'
        }
    }

    stages {
        stage('Build & Test') {
            steps {
                sh '''
                apt update
                apt install -y maven
                mvn clean test -Dcucumber.filter.tags="@Regressivo" -Dheadless=true
                '''
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}