pipeline {
  agent any

  environment {
    // FOO will be available in entire pipeline
    FOO = "PIPELINE"
  }

options {
    colorizeOutput()

    credentialsBinding {
      usernamePassword(
        "ARTIFACTORY_USERNAME",
        "ARTIFACTORY_PASSWORD",
        "mh-autonp-artifactory")
        usernamePassword(
        "",
        "STAGE1_BLUEMIX_API_KEY_MH_AUTOPP",
        "mh-autopp-stage1-bluemix-apikey")
      usernamePassword(
        "BLUEMIX_USERNAME",
        "BLUEMIX_API_KEY",
        "mh-autonp-bluemix-apikey")
      usernamePassword(
        "BLUEMIX_NONPROD_USERNAME",
        "BLUEMIX_NONPROD_API_KEY",
        "mh-autonp-bluemix-apikey")
      usernamePassword(
        "STAGE1_BLUEMIX_USERNAME",
        "STAGE1_BLUEMIX_API_KEY",
        "mh-autonp-stage1-bluemix-apikey")
    }
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
