pipeline {
  agent any

  stages {  

    stage ('Build') {
        steps {
          sh ''' 
            set +x
            echo "********************************************************"
            echo "*                                                      *"
            echo "*   🚀 Iniciando el proceso de construcción 🚀          *"
            echo "*                                                      *"
            echo "********************************************************"
            ${WORKSPACE}/gradlew build
          '''
      }
      
    }
    
    stage ('Test') {
        steps {          
          sh ''' 
            set +x
            echo "********************************************************"
            echo "*                                                      *"
            echo "*          🧪 Iniciando las pruebas 🧪                  *"
            echo "*                                                      *"
            echo "********************************************************"
            ${WORKSPACE}/gradlew build
          '''          
          script {
              compileAndroid = sh (script: 'bash +x ${WORKSPACE}/scripts/tests.sh')
          }
      }
    }

    stage('Analize battery stats') {
      steps {
        sh '''
          set +x
          echo "**********************************************************"
          echo "*                                                        *"
          echo "*   🔋 Iniciando el análisis del consumo de batería 🔋    *"
          echo "*                                                        *"
          echo "**********************************************************"
          sleep 10
        '''
        ansiColor('xterm') {
          script {
              analizeBattery = sh (script: 'bash ${WORKSPACE}/scripts/analyze.sh')
          }
        }        
      }
    }

  }
}