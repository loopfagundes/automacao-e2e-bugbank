pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser para rodar no Grid')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Rodar headless?')
        string(name: 'CUCUMBER_TAGS', defaultValue: '@Regressivo', description: 'Tags do Cucumber')
    }

    environment {
        MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-21'
        // padroniza onde o Jenkins vai publicar o Allure
        ALLURE_OUT = 'allure-results'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean previous results') {
            steps {
                sh '''
                    set +e
                    rm -rf allure-results target/allure-results results.tar.gz
                    set -e
                '''
            }
        }

        stage('Start Selenium Grid') {
            steps {
                sh '''
                    set -e

                    # compatível com docker compose (v2) e docker-compose (v1)
                    if docker compose version >/dev/null 2>&1; then
                      DC="docker compose"
                    else
                      DC="docker-compose"
                    fi

                    $DC -f docker-compose.yml down || true
                    $DC -f docker-compose.yml up -d
                '''
            }
        }

        stage('Test') {
            steps {
                sh '''
                    set -e
                    mkdir -p "${HOME}/.m2/repository"

                    # empacota o workspace e executa no container do Maven
                    tar -czf - . | docker run --rm -i \
                        -v "${HOME}/.m2/repository:/root/.m2/repository" \
                        -e BROWSER="${BROWSER}" \
                        -e HEADLESS="${HEADLESS}" \
                        -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
                        -e ALLURE_OUT="${ALLURE_OUT}" \
                        ${MAVEN_IMAGE} \
                        bash -lc '
                          set -e
                          mkdir -p /work && cd /work
                          tar -xzf - >/dev/null

                          # limpa dentro do container para evitar resíduo
                          rm -rf "$ALLURE_OUT" target/allure-results || true

                          # roda testes (não derruba o pipeline aqui, pra sempre gerar Allure)
                          mvn -B clean test \
                            -Dcucumber.filter.tags="$CUCUMBER_TAGS" \
                            -Dbrowser="$BROWSER" \
                            -Dheadless="$HEADLESS" \
                            1>&2 || true

                          # padroniza saída do Allure em um único lugar
                          if [ -d target/allure-results ] && [ ! -d "$ALLURE_OUT" ]; then
                            mkdir -p "$ALLURE_OUT"
                            cp -R target/allure-results/. "$ALLURE_OUT/" 2>/dev/null || true
                          fi

                          # exporta resultados pro Jenkins (tar só do que existir)
                          if [ -d "$ALLURE_OUT" ] && [ "$(ls -A "$ALLURE_OUT" 2>/dev/null)" ]; then
                            tar -czf - "$ALLURE_OUT"
                          else
                            # cria tar vazio (pipeline não quebra, mas você enxerga que não gerou nada)
                            tar -czf - /dev/null 2>/dev/null || true
                          fi
                        ' > results.tar.gz

                    # extrai resultados no workspace do Jenkins
                    if [ -s results.tar.gz ]; then
                      tar -xzf results.tar.gz || true
                    fi
                '''
            }
        }
    }

    post {
        always {
            sh '''
                set +e
                if docker compose version >/dev/null 2>&1; then
                  DC="docker compose"
                else
                  DC="docker-compose"
                fi
                $DC -f docker-compose.yml down || true
                set -e
            '''

            // Publica Allure SEM ficar tentando dois caminhos
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: "${ALLURE_OUT}"]]
        }
    }
}