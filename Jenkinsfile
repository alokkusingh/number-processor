pipeline {
    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
    }

    //agent any
    agent {
        dockerfile {
            additionalBuildArgs  '--build-arg JAR_FILE=target/${IMAGE}-${VERSION}.jar'
        }
    }

    stages {
        stage ('Compile Stage') {
            steps {
                echo "Building ${IMAGE} - ${VERSION}"
                withMaven(maven : 'maven-3-6-3') {
                    //sh 'mvn clean compile'
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage ('Test Stage') {
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh 'mvn test'
                }
            }
        }

        stage ('Deploy Stage') {
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh 'mvn deploy -DskipTests'
                }
            }
        }
    }
}