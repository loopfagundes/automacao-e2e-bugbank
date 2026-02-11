pipeline {
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-21'
            args '-v /root/.m2:/root/.m2'
        }
    }

    parameters {
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Executar testes headless?')
    }

    environment {
        HEADLESS = "${params.HEADLESS}"
    }

    stages {

        stage('Build') {
            steps {
                echo 'Compilando e rodando testes Cucumber'
                sh """
                mvn clean test \
                  -Dcucumber.filter.tags="@Regressivo" \
                  -Dheadless=${env.HEADLESS} \
                  -DfailIfNoTests=false
                """
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}