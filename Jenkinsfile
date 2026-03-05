pipeline {
  agent any

  parameters {
    choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser para rodar no Grid')
    booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Rodar headless?')
    string(name: 'CUCUMBER_TAGS', defaultValue: '@Regressivo', description: 'Tags do Cucumber')
  }

  environment {
    MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-21'
    ALLURE_OUT  = 'allure-results'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
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
        script {
          sh '''
            set -e

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
    }

    stage('Test') {
      steps {
        sh '''
          set -e

          M2_CACHE="${HOME}/.m2/repository"
          mkdir -p "${M2_CACHE}"

          tar -czf - . | docker run --rm -i -v "${M2_CACHE}:/root/.m2/repository" -e BROWSER="${BROWSER}" -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" -e ALLURE_OUT="${ALLURE_OUT}" "${MAVEN_IMAGE}"

            bash -lc '
              set -e

              mkdir -p /work && cd /work
              tar -xzf - >/dev/null

              rm -rf "$ALLURE_OUT" target/allure-results || true

              mvn -B clean test -Dcucumber.filter.tags="$CUCUMBER_TAGS" -Dbrowser="$BROWSER" -Dheadless="$HEADLESS" 1>&2 || true

              if [ -d target/allure-results ] && [ ! -d "$ALLURE_OUT" ]; then
                mkdir -p "$ALLURE_OUT"
                cp -R target/allure-results/. "$ALLURE_OUT/" 2>/dev/null || true
              fi

              if [ -d "$ALLURE_OUT" ] && [ "$(ls -A "$ALLURE_OUT" 2>/dev/null)" ]; then
                tar -czf - "$ALLURE_OUT"
              else
                tar -czf - /dev/null 2>/dev/null || true
              fi
            ' > results.tar.gz

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

      allure includeProperties: false,
             jdk: '',
             results: [[path: "${ALLURE_OUT}"]]
    }
  }
}