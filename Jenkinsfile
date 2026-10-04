pipeline {
  agent any

  environment {
    IMAGE  = 'docker.io/akshat615/akshat-task-manager'
    TAG    = "${env.BUILD_NUMBER}"
    DB_URL = 'jdbc:postgresql://host.docker.internal:5432/task_management'
  }

  stages {
    stage('build') {
      steps {
        sh 'docker build -t "$IMAGE:$TAG" -t "$IMAGE:latest" .'
      }
    }

    stage('push') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub',
          usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PWD')]) {
          sh 'echo "$DOCKERHUB_PWD" | docker login -u "$DOCKERHUB_USER" --password-stdin'
          sh 'docker push "$IMAGE:$TAG"'
          sh 'docker push "$IMAGE:latest"'
        }
      }
    }

    stage('deploy') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'postgres-db',
          usernameVariable: 'SPRING_DATASOURCE_USERNAME',
          passwordVariable: 'SPRING_DATASOURCE_PASSWORD')]) {
          sh 'docker rm -f task-manager || true'
          sh '''
            docker run -d --name task-manager -p 8000:8000 \
              -e SPRING_DATASOURCE_URL="$DB_URL" \
              -e SPRING_DATASOURCE_USERNAME \
              -e SPRING_DATASOURCE_PASSWORD \
              "$IMAGE:$TAG"
          '''
        }

        sh '''
cat > deploy-info-$BUILD_NUMBER.txt <<EOF
build: $BUILD_NUMBER
image: $IMAGE:$TAG
commit: $GIT_COMMIT
branch: $GIT_BRANCH
time: $(date -u +"%Y-%m-%dT%H:%M:%SZ")
url: $BUILD_URL
EOF
        '''
        archiveArtifacts artifacts: "deploy-info-${BUILD_NUMBER}.txt", fingerprint: true
      }
    }

    stage('test') {
      steps {
        sh '''
          for i in $(seq 1 15); do
            if curl -s -o /dev/null http://host.docker.internal:8000; then
              echo "App is up"
              exit 0
            fi
            echo "Waiting for app... ($i)"
            sleep 2
          done
          echo "App did not start. Container logs:"
          docker logs --tail 50 task-manager
          exit 1
        '''
      }
    }
  }

  post {
    success { echo "Build ${env.BUILD_NUMBER} succeeded" }
    failure { echo "Build ${env.BUILD_NUMBER} failed" }
    always  {
      sh 'docker logout || true'
      cleanWs()
      echo "Build ${env.BUILD_NUMBER} finished"
    }
  }
}