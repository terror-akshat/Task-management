pipeline {
  agent any

  environment {
    IMAGE = 'docker.io/akshat615/akshat-task-manager'
    TAG = 'placeholder'
  }

  stages {
    stage('Prepare') {
        steps {
            script {
                env.TAG = "${env.BRANCH_NAME.toLowerCase().replaceAll(/[^a-z0-9_.-]/, '-')}-${env.BUILD_NUMBER}"
            }
        }
    }

    stage('build') {
  steps {
    sh '''
      docker build -t "$IMAGE:$TAG" .
      if [ "$BRANCH_NAME" = "main" ]; then
        docker tag "$IMAGE:$TAG" "$IMAGE:latest"
      fi
    '''
  }
}

    stage('push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub',
          usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PWD')]) {
          sh 'echo "$DOCKERHUB_PWD" | docker login -u "$DOCKERHUB_USER" --password-stdin'
          ssh 'docker push "$IMAGE:$TAG"'

sh '''
  if [ "$BRANCH_NAME" = "main" ]; then
    docker push "$IMAGE:latest"
  fi
'''
          sh 'docker logout'
        }
      }
    }

    stage('deploy') {
      when {
        branch 'main'
      }
      steps {
        // Remove a container left over from the old `docker run` deploy
        sh 'docker rm -f task-manager || true'
        // Starts Postgres + app together; IMAGE/TAG come from the environment block
        sh 'docker compose up -d --no-build'

        sh '''
cat > deploy-info-$BUILD_NUMBER.txt <<EOF
build: $BUILD_NUMBER
image: $IMAGE:$TAG
commit: ${GIT_COMMIT}
branch: $GIT_BRANCH
time: $(date -u +"%Y-%m-%dT%H:%M:%SZ")
url: $BUILD_URL
EOF
        '''
        archiveArtifacts artifacts: "deploy-info-${BUILD_NUMBER}.txt", fingerprint: true
      }
    }

    stage('test') {
      when {
    branch 'main'
}
      steps {
        // Poll /health from inside the app container so it works even when Jenkins runs in Docker
        sh '''
          for i in $(seq 1 30); do
            if docker compose exec -T app wget -qO- http://localhost:8001/health; then
              echo; echo "App is healthy"; exit 0
            fi
            sleep 2
          done
          echo "App did not become healthy in 60s"
          docker compose logs app
          exit 1
        '''
      }
    }
  }

  post {
    success { echo "Build ${env.BUILD_NUMBER} succeeded" }
    failure { echo "Build ${env.BUILD_NUMBER} failed" }
    always {
      echo "Build ${env.BUILD_NUMBER} finished"
      cleanWs()
    }
  }
}
