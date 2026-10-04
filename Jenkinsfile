pipeline {
  agent any

  environment {
    IMAGE = 'docker.io/akshat615/akshat-task-manager'   
    TAG   = "${env.BUILD_NUMBER}"                  
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
        sh 'docker pull "$IMAGE:$TAG"'
        sh 'docker rm -f taks-manager || true'
        sh 'docker run -d --name taks-manager -p 5000:5000 "$IMAGE:$TAG"'

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
      steps {
        sh 'sleep 2; curl -s http://localhost:5000 || true'
      }
    }

    stage('cleanup') {
      steps {
        cleanWs()
      }
    }
  }

  post {
    success { echo "Build ${env.BUILD_NUMBER} succeeded" }
    failure { echo "Build ${env.BUILD_NUMBER} failed" }
    always  { echo "Build ${env.BUILD_NUMBER} finished" }
  }
}
