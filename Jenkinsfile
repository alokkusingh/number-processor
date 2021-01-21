pipeline {
    agent any

    environment {
        //BRANCH = "${env.GIT_BRANCH.split("/")[1]}"
        BRANCH = "${env.BRANCH_NAME}"
        DOCKER_REGISTRY = getDockerRegistry(BRANCH)
        DOCKER_TLS_VERIFY = "1"
        DOCKER_HOST = "tcp://192.168.99.104:2376"
        DOCKER_CERT_PATH = "/Users/aloksingh/.docker/machine/machines/default"
        DOCKER_MACHINE_NAME = "default"
        ENV_NAME = getEnvName(BRANCH)
        AWS_CLI_KEY = getAwsCliKey(BRANCH)
        AWS_CLI_SECRET = getAwsCliSecret(BRANCH)
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables - pipeline-utility-steps plugin
        ARTIFACT = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
        SKIP_BUILD = skipBuild(BRANCH)
    }

    stages {
        stage ('Compile, Test and Package') {
            when {
                expression { SKIP_BUILD != true}
            }
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh './mvnw clean jxr:jxr verify package surefire-report:report-only'
                }
            }
        }

        stage ('Deploy Artifact') {
            when {
                expression { SKIP_BUILD != true}
            }
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    echo "Skipping for now!"
                    //sh 'mvn deploy -DskipTests'
                }
            }
        }

        stage ('Build Docker Image') {
            when {
                expression { SKIP_BUILD != true}
            }
            steps {
                echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                script {
                    if (BRANCH == 'master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else if (BRANCH == 'dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image') {
            when {
                expression { SKIP_BUILD != true}
            }
            steps {
                script {
                    if (BRANCH == 'master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'dev') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                    } else {
                        echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                    }
                }
            }
        }
    }
}

def getEnvName(branchName) {
    if( branchName == "master") {
        return "prod";
    } else if (branchName == "dev") {
        return "dev";
    } else {
        return "future";
    }
}


def getDockerRegistry(branchName) {
    if( branchName == "master") {
        return "alokkusingh";
    } else if (branchName == "dev") {
        return "alokkusingh";
    } else {
        return "unknown";
    }
}

def getAwsCliKey(branchName) {
    if( branchName == "master") {
        return "";
    } else if (branchName == "dev") {
        return "";
    } else {
        return "unknown";
    }
}

def getAwsCliSecret(branchName) {
    if( branchName == "master") {
        return "";
    } else if (branchName == "dev") {
        return "";
    } else {
        return "unknown";
    }
}

def skipBuild(branchName) {
    echo "Branch Name: ${branchName}"
    if( branchName == "master") {
        echo "Master"
        return false;
    } else if (branchName == "dev") {
        echo "Dev"
        return false;
    } else {
        echo "Other"
        return true;
    }
}