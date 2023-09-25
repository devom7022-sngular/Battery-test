pipeline {
  agent any
  parameters {
    choice(
        choices: ['manual', 'automated'],
        description: 'Selection to make tests',
        name: 'TESTS_TYPE')
    string (
        defaultValue: '2',
        description: 'Time (in minutes) to keep android emulator active',
        name: 'EMULATOR_ACTIVE')
  }

  stages {  

    stage ('Build') {
        steps {
          sh ''' 
            set
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
            set
            echo "********************************************************"
            echo "*                                                      *"
            echo "*          🧪 Iniciando las pruebas 🧪                  *"
            echo "*                                                      *"
            echo "********************************************************"
            ${WORKSPACE}/gradlew build
          '''          
          script {
              compileAndroid = sh (script: 'bash ${WORKSPACE}/scripts/tests.sh ${TESTS_TYPE} ${EMULATOR_ACTIVE}')
          }
      }
    }

    stage('Analize battery stats') {
      steps {
        sh '''
          set
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