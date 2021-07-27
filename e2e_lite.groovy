pipeline {
  agent any

  environment {
    // FOO will be available in entire pipeline
  

     /*
       * Uses a Jenkins credential called "FOOCredentials" and creates environment variables:
       * "$FOO" will contain string "USR:PSW"
       * "$FOO_USR" will contain string for Username
       * "$FOO_PSW" will contain string for Password
       */
      FOO = credentials("STAGE1_BLUEMIX_API_KEY")
  }


    
  stages {
    stage("local") {
      environment {
        // BAR will only be available in this stage
        BAR = "STAGE"
      }
      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
        // sh 'echo ${env.STAGE1_BLUEMIX_API_KEY}'
        sh 'echo ${STAGE1_BLUEMIX_API_KEY}'
        sh 'echo user id: $FOO_USR'
      }
    }
    stage("global") {

        agent {
            kubernetes {
            //cloud 'kubernetes'
            label 'mypod'
            containerTemplate {
                name 'maven'
                image 'maven:3.3.9-jdk-8-alpine'
                ttyEnabled true
                command 'cat'
            }
            }
        }

      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
     
      }
    }
  }
}
