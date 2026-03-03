

# 🏗️ Infraestrutura - Automação BugBank

Este documento explica como configurar o ambiente completo do zero.

- **Complexidade** - ⭐⭐⭐⭐⭐

---  

# 📦 Pré-requisitos

Instalar:

- Docker
- Docker Compose
- Jenkins (localmente)
- Selenium Grid (local / docker-compose)

---  
# 🐳 Instalar Docker

Windows / Mac:  
[Download - Docker Desktop](https://www.docker.com/products/docker-desktop)

Linux:
```bash
sudo apt update  
```
```bash
sudo apt install docker.io docker-compose
```

Verificar instalação:
```bash
docker --version  
```
```bash
docker-compose --version
```
  
---  

# 🧩 Selenium Grid (Local)

O projeto utiliza Selenium Grid via Docker para execução remota dos testes.

### Arquitetura
- selenium-hub
- node-chrome
- node-firefox
- node-edge

Criar um arquivo na raiz do projeto `docker-compose.yml`
```yaml
services:  
  selenium-hub:  
    image: selenium/hub:latest  
    ports:  
      - "4444:4444"  
  
  chrome:  
    image: selenium/node-chrome:latest  
    environment:  
      - SE_EVENT_BUS_HOST=selenium-hub  
      - SE_EVENT_BUS_PUBLISH_PORT=4442  
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443  
    depends_on:  
      - selenium-hub  
  
  firefox:  
    image: selenium/node-firefox:latest  
    environment:  
      - SE_EVENT_BUS_HOST=selenium-hub  
      - SE_EVENT_BUS_PUBLISH_PORT=4442  
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443  
    depends_on:  
      - selenium-hub  
  
  edge:  
    image: selenium/node-edge:latest  
    environment:  
      - SE_EVENT_BUS_HOST=selenium-hub  
      - SE_EVENT_BUS_PUBLISH_PORT=4442  
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443  
    depends_on:  
      - selenium-hub
```  
Executar um contêiner do Docker
```bash
docker-compose up -d
```

Acessar UI:
```
http://localhost:4444/ui/
```

Parar:
```bash
docker-compose down
```

### 🔹 Execução Local
Criar um arquivo na raiz do projeto `docker-compose.override.yml`
```yaml
services:  
  selenium-hub:  
    image: selenium/hub:latest  
    ports:  
      - "4445:4444"
```
Porta:
```
4445 → Hub
```

Subir:
```bash  
docker-compose up -d  
```
Acessar:
```
http://localhost:4445/ui
```

#### 🧠 Por que separar 4444 e 4445?

-   4444 = CI (Jenkins)
-   4445 = Local (dev)
-   Evita conflito de porta
-   Permite rodar local e pipeline ao mesmo tempo

  
---  

# 🔁 Subir Jenkins via Docker

Criar container Jenkins:

```bash
docker run -d --name jenkins --restart=unless-stopped -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:lts
```

#### 📌 Observação

O Jenkins utiliza o Docker do host através do socket:
```
/var/run/docker.sock
```
---  

## 🔑 Acessar Jenkins
```
http://localhost:8080
```
Para pegar senha inicial:
```bash
docker logs jenkins
```

#### 📌 Observação

**SE:**

- A imagem do Jenkins não tiver `docker` instalado
- O comando `docker` não funcionar dentro do container

**Não precisa** criar Dockerfile dentro do projeto

O **projeto automação** não deve misturar Jenkins Dockerfile.

### 📂 Criar o Jenkins em uma pasta separada.

### PASSO 1 - criar pasta `jenkins-docker`

```
mkdir  C:\jenkins-docker  
cd  C:\jenkins-docker
```

### PASSO 2 - criar Dockerfile

Criar um arquivo chamado **Dockerfile** com esse conteúdo:
```dockerfile
FROM jenkins/jenkins:lts  
  
USER root  
  
RUN apt-get update && apt-get install -y docker.io docker-compose  
  
USER jenkins
```

Criar **manualmente** no notepad ou VSCode, mas tem que ser sem **.txt**.

### PASSO 3 - o comando correto de build

```bash
docker build -t jenkins-docker .
 ```

### PASSO 4 - remover o container atual
```bash
docker rm -f jenkins
```
Subir Jenkins como **root** (adiciona `-u root`) :

```bash
docker run -d --name jenkins --restart=unless-stopped -u root -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins-docker
```  
---  

# 🧪 Configurar Pipeline

1. Criar Multibranch Pipeline
2. Conectar GitHub com Token
3. Scan Repository Now
4. Jenkinsfile será detectado automaticamente

Cria um arquivo raiz do projeto `Jenkinsfile`
```groovy
pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser para rodar no Grid')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Rodar headless?')
        string(name: 'CUCUMBER_TAGS', defaultValue: '@Regressivo', description: 'Tags do Cucumber. Ex: @Regressivo or @Smoke and not @Wip')
    }

    environment {
        MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-21'
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Start Selenium Grid') {
            steps {
                sh '''
          set -e
          docker-compose -f docker-compose.yml down || true
          docker ps -q --filter "publish=4444" | xargs -r docker rm -f || true
          docker-compose -f docker-compose.yml up -d
          docker ps
        '''
            }
        }


        stage('Test') {
            steps {
                sh '''
          set -e
          echo "Params:"
          echo "  BROWSER=${BROWSER}"
          echo "  HEADLESS=${HEADLESS}"
          echo "  CUCUMBER_TAGS=${CUCUMBER_TAGS}"
          tar -czf - . | docker run --rm -i \
            -e BROWSER="${BROWSER}" \
            -e HEADLESS="${HEADLESS}" \
            -e CUCUMBER_TAGS="${CUCUMBER_TAGS}" \
            ${MAVEN_IMAGE} \
            bash -lc '
              set -e
              mkdir -p /work && cd /work
              tar -xzf -
              mvn clean
              mvn -q test -Dcucumber.filter.tags="${CUCUMBER_TAGS}"
            '
        '''
            }
        }
    }

    post {
        always {
            sh '''
        docker-compose down || true
      '''
        }
    }
}
```

### 🔄**A pipeline sobe e derruba o Grid automaticamente.**

---  

## ▶️ Comandos úteis: Jenkins e Selenium Grid no Docker

Powershell ou terminal dentro do Docker:

- **Jenkins**
```
docker start jenkins

docker stop jenkins
```
- **Selenium Grid**
```
cd automacao-e2e-bugbank

docker-compose -up -d

docker-compose down
```

Porta:
```
4444 → Hub  
4445 →  Local
```