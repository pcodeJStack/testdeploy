pipeline {
    agent any

    environment {
        IMAGE_NAME = "phucitdev/be-pickleball:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/pcodeJStack/testdeploy.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh '''
                chmod +x mvnw
                ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t $IMAGE_NAME .
                '''
            }
        }

        stage('Push Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    docker push $IMAGE_NAME
                    '''
                }
            }
        }

        stage('Deploy to VPS') {
            steps {
                sh '''
                ssh -i ~/.ssh/id_rsa_jenkins root@168.144.139.227 "

                docker pull phucitdev/be-pickleball:latest &&
                docker stop pickleball-be || true &&
                docker rm pickleball-be || true &&
                docker run -d \
                --name pickleball-be \
                --network pickleball-network \
                -p 8080:8080 \
                --restart always \
                --env-file /opt/app/.env \
                phucitdev/be-pickleball:latest                "
                '''
            }
        }
    }
}