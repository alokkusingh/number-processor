pipeline {
    agent any

    environment {
        BRANCH = "${env.GIT_BRANCH}"
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
        DO_NOT_SKIP = skipBuild(BRANCH)
    }

    stages {
        stage ('Compile, Test and Package') {
            when {
                expression { DO_NOT_SKIP == true}
            }
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh './mvnw clean verify package surefire-report:report-only'
                }
            }
        }

        stage ('Deploy Artifact') {
            when {
                expression { DO_NOT_SKIP == true}
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
                expression { DO_NOT_SKIP == true}
            }
            steps {
                echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                script {
                    if (BRANCH == 'origin/master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else if (BRANCH == 'origin/dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image') {
            when {
                expression { DO_NOT_SKIP == true}
            }
            steps {
                script {
                    if (BRANCH == 'origin/master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'origin/dev') {
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
    if("origin/master".equals(branchName)) {
        return "prod";
    } else if ("origin/dev".equals(branchName)) {
        return "dev";
    } else {
        return "future";
    }
}


def getDockerRegistry(branchName) {
    if("origin/master".equals(branchName)) {
        return "alokkusingh";
    } else if ("origin/dev".equals(branchName)) {
        return "alokkusingh";
    } else {
        return "unknown";
    }
}

def getAwsCliKey(branchName) {
    if("origin/master".equals(branchName)) {
        return "";
    } else if ("origin/dev".equals(branchName)) {
        return "";
    } else {
        return "unknown";
    }
}

def getAwsCliSecret(branchName) {
    if("origin/master".equals(branchName)) {
        return "";
    } else if ("origin/dev".equals(branchName)) {
        return "";
    } else {
        return "unknown";
    }
}

def skipBuild(branchName) {
    if("origin/master".equals(branchName)) {
        return false;
    } else if ("origin/dev".equals(branchName)) {
        return false;
    } else {
        return true;
    }

}