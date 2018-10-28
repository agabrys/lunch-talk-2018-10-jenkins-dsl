pipelineJob('pipeline-sp') {
	logRotator(-1, 20, -1, 20)
	parameters {
		stringParam('PRODUCT_REPO_URL', '', '')
		stringParam('CHANGED_BRANCH', '', '')
		stringParam('PRIORITY', '', '')
	}
	definition {
		cps {
			script(readFileFromWorkspace('jobs/pipeline-sp.jenkinsfile'))
		}
	}
}
