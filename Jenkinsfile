pipeline {
    agent any

    parameters {
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Executar testes headless?')
    }

    environment {
        HEADLESS = "${params.HEADLESS}"
    }

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out branch: ${env.BRANCH_NAME}"
            }
        }

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
            post {
                always {
                    echo "Arquivando relatórios de teste..."
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline finalizado com sucesso!'
        }
        failure {
            echo 'Pipeline falhou!'
        }
    }
}