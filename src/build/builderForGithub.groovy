def buildGolangProject(params) {
    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        // sh "git clone https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${params['OWNER']}/${params['REPO']}.git"
        changeLogSets = checkout(changelog: true, poll: false, scm: [
            $class: 'GitSCM',
            branches: [[name: "${params.BUILD_BRANCH}"]],],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'CleanCheckout']],
            submoduleCfg: [],
            userRemoteConfigs: [[url: "ssh://${GIT_USERNAME}@github.com/${params['OWNER']}/${params['REPO']}.git"]]
        )
        echo "GIT_COMMIT: ${changeLogSets['GIT_COMMIT']}"
        sh "go build ."
        sh "echo built success"
    }
}