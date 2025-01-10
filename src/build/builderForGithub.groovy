def buildGolangProject(params) {
    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        echo "OWNER: ${params['OWNER']}"
        echo "REPO: ${params['REPO']}"
        echo "BUILD_BRANCH: ${params.BUILD_BRANCH}"
        // sh "git clone https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${params['OWNER']}/${params['REPO']}.git"
        changeLogSets = checkout(changelog: true, poll: false, scm: [
            $class: 'GitSCM',
            branches: [[name: "${params.BUILD_BRANCH}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'CleanCheckout']],
            submoduleCfg: [],
            userRemoteConfigs: [[url: "https://github.com/${params['OWNER']}/${params['REPO']}.git"]]]
        )
        echo "GIT_COMMIT: ${changeLogSets['GIT_COMMIT']}"
        sh "go build ."
        sh "echo built success"
    }
}