def call (Map config = [:]) {
    withCredentials([
        sshUserPrivateKey(
            credentialsId: ${config.cred_id},
            keyFileVariable: 'SSH_KEY'
        )
    ]) {
        withEnv(["GIT_SSH_COMMAND=ssh -o StrictHostKeyChecking=no -i ${SSH_KEY}"]) {
            sh 'git clone --single-branch -b develop git@github.com:Sphere-Research/sphere-devops.git'
            if ("${config.path}") {
                dir("config.path") {
                    sh "git clone --single-branch -b ${config.branch} ${config.repo}"
                }
            } else {
                sh "git clone --single-branch -b ${config.branch} ${config.repo}"
            }
        }
    }
}