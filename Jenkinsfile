
pipeline {
  agent any

  environment {
    IMAGE = 'docker.io/akshat615/akshat-task-manager'
    TAG   = 'placeholder'
  }

  stages {
    stage('Prepare') {
      steps {
        script {
          env.TAG = "${env.BRANCH_NAME.toLowerCase().replaceAll(/[^a-z0-9_.-]/, '-')}-${env.BUILD_NUMBER}"
          echo "Branch: ${env.BRANCH_NAME}"
          echo "Image tag: ${env.TAG}"
        }
      }
    }

    stage('Checkout and Maven Tests') {
      steps {
        sh './mvnw test'
      }
    }

    stage('Build Docker Image') {
      when {
        branch 'main'
      }
      steps {
        sh '''
          docker build -t "$IMAGE:$TAG" .
          docker tag "$IMAGE:$TAG" "$IMAGE:latest"
        '''
      }
    }

    stage('Push Docker Image') {
      when {
        branch 'main'
      }
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'dockerhub',
          usernameVariable: 'DOCKERHUB_USER',
          passwordVariable: 'DOCKERHUB_PWD'
        )]) {
          sh '''
            set +x
            echo "$DOCKERHUB_PWD" | docker login \
              -u "$DOCKERHUB_USER" --password-stdin

            docker push "$IMAGE:$TAG"
            docker push "$IMAGE:latest"
            docker logout
          '''
        }
      }
    }

    stage('Deploy to Docker Compose') {
      when {
        branch 'main'
      }
      steps {
        sh '''
          docker compose up -d --no-build --force-recreate
        '''

        sh '''
          cat > deploy-info-$BUILD_NUMBER.txt <<EOF
build: $BUILD_NUMBER
image: $IMAGE:$TAG
commit: ${GIT_COMMIT}
branch: ${BRANCH_NAME}
time: $(date -u +"%Y-%m-%dT%H:%M:%SZ")
url: $BUILD_URL
EOF
        '''

        archiveArtifacts artifacts: "deploy-info-${BUILD_NUMBER}.txt",
                         fingerprint: true
      }
    }

    stage('Health Check') {
      when {
        branch 'main'
      }
      steps {
        sh '''
          for i in $(seq 1 30); do
            if docker compose exec -T app \
              wget -qO- http://localhost:8001/health; then
              echo
              echo "App is healthy"
              exit 0
            fi
            sleep 2
          done

          echo "App did not become healthy in 60 seconds"
          docker compose logs app
          exit 1
        '''
      }
    }
  }

  post {
    success {
      echo "Build ${env.BUILD_NUMBER} succeeded"
    }
    failure {
      echo "Build ${env.BUILD_NUMBER} failed"
    }
    always {
      echo "Build ${env.BUILD_NUMBER} finished"
      cleanWs()
    }
  }
}
