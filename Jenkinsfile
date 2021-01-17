pipeline {
    agent any

    stages {
        stage ('Compile Stage') {
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Test Stage') {
                withMaven(maven : 'maven-3-6-3') {
                    sh 'mvn test'
                }
        }

        stage ('Deploy Stage') {
                withMaven(maven : 'maven-3-6-3') {
                    sh 'mvn deploy'
                }
        }

        stage ('Create Docker Image Stage') {
        }

        stage ('Deploy Docker Image Stage') {
        }
    }
}