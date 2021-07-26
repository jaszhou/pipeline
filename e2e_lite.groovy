pipeline {
  agent any

  environment {
    // FOO will be available in entire pipeline
    FOO = "PIPELINE"
  }

wrappers {
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
        sh '''
                    docker run --rm \
            --network=host \
            --name cli-lite-test \
            -e PUBLIC_API_ENDPOINT="test.cloud.ibm.com" \
            -e ES_PLUGIN_LOCATION="/es-plugin_linux_amd64" \
            -e INSTANCE_NAME="hyperion-lite" \
            -e SERVICE_KEY_NAME="hyperion-lite-api-key" \
            -e STAGE1_BLUEMIX_API_KEY="${STAGE1_BLUEMIX_API_KEY}" \
            -e STAGE1_BLUEMIX_API_KEY_MH_AUTOPP="${STAGE1_BLUEMIX_API_KEY_MH_AUTOPP}" \
            -e CLI_TEST_TAGS="not @enterprise and not @lite-skip" \
            "cli-e2e-test:latest"

        '''
      }
    }
  }
}
