def buildGolangProject(params) {
    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        changeLogSets = checkout(changelog: true, poll: false, scm: [
            $class: 'GitSCM',
            branches: [[name: "${params.BUILD_BRANCH}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'CleanCheckout']],
            submoduleCfg: [],
            userRemoteConfigs: [[url: "https://github.com/${params['OWNER']}/${params['REPO']}.git"]]]
        )
        echo "GIT_COMMIT: ${changeLogSets['GIT_COMMIT']}"
        sh "scripts/${params['TARGET']}_build.sh"
        sh "echo built successfully"
    }
}