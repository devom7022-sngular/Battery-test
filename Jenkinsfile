pipeline {
  agent any

  stages {

    stage ('Checkout') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/dev']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/HeribertoME/Battery-test/']]])
      }      
    }

    stage ('Build') {
        steps {
        sh """ 
          echo "*****************************"
          echo "* Build :)*"
          echo "*****************************"
          ${WORKSPACE}/gradlew build
        """
      }
      
    }
    
    stage ('Test') {
        steps {
          // Launch emulator and install debug app
          script {
              compileAndroid = sh (script: 'bash -x ${WORKSPACE}/scripts/tests.sh')
          }
      }
    }

    stage('Analize battery stats') {
      steps {
        sh """ 
          echo "*****************************"
          echo "*Analizing Battery Stats :)*"
          echo "*****************************"
          sleep 30
        """
        // TODO Call script for analice battery stats file
      }
    }

  }
}