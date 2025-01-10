def fetchCode(owner, repo, branch) {
    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        changeLogSets = checkout(changelog: true, poll: false, scm: [
            $class: 'GitSCM',
            branches: [[name: "${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'CleanCheckout']],
            submoduleCfg: [],
            userRemoteConfigs: [[url: "https://github.com/${owner}/${repo}.git"]]]
        )
        echo "GIT_COMMIT: ${changeLogSets['GIT_COMMIT']}"
    }
}