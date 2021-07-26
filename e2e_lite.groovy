pipeline {
  agent any

  environment {
    // FOO will be available in entire pipeline
    FOO = "PIPELINE"
  }


    
  stages {
    stage("local") {
      environment {
        // BAR will only be available in this stage
        BAR = "STAGE"
      }
      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
        sh 'echo ${env.STAGE1_BLUEMIX_API_KEY}'
        sh 'echo ${STAGE1_BLUEMIX_API_KEY}'
      }
    }
    stage("global") {
      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
     
      }
    }
  }
}
