pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'alokkusingh'
        DOCKER_TLS_VERIFY = "1"
        DOCKER_HOST = "tcp://192.168.99.104:2376"
        DOCKER_CERT_PATH = "/Users/aloksingh/.docker/machine/machines/default"
        DOCKER_MACHINE_NAME = "default"

        script {
            if (env.GIT_BRANCH == 'origin/master') {
                ENV_NAME = "prod"
            } else if (env.GIT_BRANCH == 'origin/dev') {
                ENV_NAME = "dev"
            } else {
                echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                ENV_NAME = ""
            }
        }
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables - pipeline-utility-steps plugin
        ARTIFACT = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
    }

    stages {
        stage ('Compile, Test and Package') {
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh './mvnw clean verify package'
                }
            }
        }

        stage ('Deploy Artifact') {
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    echo "Skipping for now!"
                    //sh 'mvn deploy -DskipTests'
                }
            }
        }

        stage ('Build Docker Image') {
            steps {
                echo "Building ${ARTIFACT} - ${VERSION}"
                script {
                    if (env.GIT_BRANCH == 'origin/master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else if (env.GIT_BRANCH == 'origin/dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }
    }

    post {
        always {
          archiveArtifacts artifacts: 'target/**/*.jar'
          junit 'target/**/*.xml'
        }
        success {
            script {
                if (env.GIT_BRANCH == 'origin/master') {
                    sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                    sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                } else if (env.GIT_BRANCH == 'origin/dev') {
                    sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                    sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                } else {
                    echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                }
            }
        }
      }
}
