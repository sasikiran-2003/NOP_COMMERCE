pipeline {
    agent any

    environment {
        APP_ENV = 'dev'
    }

    stages {
        stage('Clone') {
            steps {
                echo 'Cloning repository...'
                git branch: 'main', url: 'https://github.com/sasikiran-2003/NOP_COMMERCE.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Building the project and running TestNG tests with Maven...'
                // Clean + compile + run tests
                bat 'mvn clean test'
            }
        }

        stage('Commit & Push Changes') {
            steps {
                script {
                    echo 'Checking for changes to push...'
                    bat """
                        git config user.email "jenkins@pipeline.com"
                        git config user.name "Jenkins CI"

                        git status
                        git add .

                        REM Commit only if there are changes
                        git diff --cached --quiet || git commit -m "Jenkins: Auto-commit after build"

                        REM Push to GitHub
                        git push origin ${env.GIT_BRANCH}
                    """
                }
            }
        }

        stage('Publish Reports') {
            steps {
                echo 'Publishing ExtentReports in Jenkins...'
                publishHTML([
                    reportDir: 'reports',    // adjust if your ExtentReports folder differs
                    reportFiles: 'ExtentReports.html',
                    reportName: 'Extent Report',
                    keepAll: true,
                    allowMissing: false,
                    alwaysLinkToLastBuild: true
                ])
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying to ${env.APP_ENV} environment..."
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed! Please check Jenkins logs and ExtentReport.'
        }
    }
}
