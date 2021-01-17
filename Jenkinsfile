pipeline {
    agent any
    //agent {
    //    dockerfile {
    //        additionalBuildArgs  "--build-arg JAR_FILE=target/${IMAGE}-${VERSION}.jar"
    //    }
    //}

    environment {
        DOCKER_REGISTRY = 'alokkusingh'
        DOCKER_TLS_VERIFY = "1"
        DOCKER_HOST = "tcp://192.168.99.104:2376"
        DOCKER_CERT_PATH = "/Users/aloksingh/.docker/machine/machines/default"
        DOCKER_MACHINE_NAME = "default"
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables - pipeline-utility-steps plugin
        ARTIFACT = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
    }

    stages {
        //stage ('Compile') {
        //    steps {
        //        echo "Building ${ARTIFACT} - ${VERSION}"
        //        withMaven(maven : 'maven-3-6-3') {
        //            //sh 'mvn clean compile'
        //            sh 'mvn clean package -DskipTests'
        //        }
        //    }
        //}

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
                sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar ."
            }
        }
    }

    post {
        always {
          archiveArtifacts artifacts: 'target/**/*.jar'
          junit 'target/**/*.xml'
        }
        success {
            sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
            sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
        }
      }
}