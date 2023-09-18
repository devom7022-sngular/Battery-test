pipeline {
  agent any

  stages {  

    stage ('Build') {
        steps {
        sh ''' 
          set +x
          echo "*****************************"
          echo "* Build :)*"
          echo "*****************************"
          ${WORKSPACE}/gradlew build
        '''
      }
      
    }
    
    stage ('Test') {
        steps {
          // Launch emulator and install debug app
          script {
              compileAndroid = sh (script: 'bash +x ${WORKSPACE}/scripts/tests.sh')
          }
      }
    }

    stage('Analize battery stats') {
      steps {
        sh '''
          set +x
          echo "*****************************"
          echo "*Analizing Battery Stats :)*"
          echo "*****************************"
          sleep 30
        '''
        script {
              analizeBattery = sh (script: 'bash ${WORKSPACE}/scripts/analyze.sh')
          }
      }
    }

  }
}