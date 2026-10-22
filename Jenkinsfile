pipeline {
    agent any
    tools { jdk 'JDK21'; maven 'Maven3' }
    stages {

        stage('Build') {
        steps {
            bat 'mvn -B clean test-compile'
        }
        }

        stage ('Run Selenium Tests') {
            steps {
                bat 'mvn -B test'
            }
        }

    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/TEST-*.xml'
            archiveArtifacts artifacts: 'target/screenshots/**/*', allowEmptyArchive: true
        }
    }
}
