pipelineJob("n00-trigger-pipeline-manually") {

	logRotator {
		daysToKeep(90)
		artifactDaysToKeep(90)
	}

	definition {
		cpsScm {
			scm {
				git {
					remote { url('https://github.com/agabrys/lunch-talk-2018-10-jenkins-dsl.git') }
					branch('master')
					extensions {
						cloneOptions {
							depth(1)
							noTags()
						}
						wipeOutWorkspace()
					}
				}
				scriptPath('jobs/n00-trigger-pipeline-manually.jenkinsfile')
			}
		}
	}

	configure { project ->
		project / 'properties' << 'hudson.model.ParametersDefinitionProperty' {
			parameterDefinitions {
				'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
					delegate.name('PRODUCT_REPO_URL')
					delegate.description('ssh URL of the product repository')
					delegate.quoteValue(false)
					delegate.saveJSONParameterToFile(false)
					delegate.visibleItemCount(10)
					delegate.type('PT_SINGLE_SELECT')
					delegate.groovyScript("""import hudson.slaves.EnvironmentVariablesNodeProperty
import jenkins.model.Jenkins

Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars.get('PRODUCT_REPOSITORIES')""")
					delegate.defaultGroovyScript("""import hudson.slaves.EnvironmentVariablesNodeProperty
import jenkins.model.Jenkins

Jenkins.get().globalNodeProperties.get(EnvironmentVariablesNodeProperty.class).envVars.get('PRODUCT_REPOSITORY_DEFAULT')""")
					delegate.multiSelectDelimiter(',')
				}

				'hudson.plugins.validating__string__parameter.ValidatingStringParameterDefinition' {
					delegate.name('CHANGED_BRANCH')
					delegate.description('Product branch which will be updated.')
					delegate.regex('.+')
					delegate.failedValidationMessage('The parameter is required.')
				}

				'com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition' {
					delegate.name('PRIORITY')
					delegate.quoteValue(false)
					delegate.saveJSONParameterToFile(false)
					delegate.visibleItemCount(5)
					delegate.type('PT_RADIO')
					delegate.value('1,2,3,4,5')
					delegate.defaultValue(3)
					delegate.multiSelectDelimiter(',')
				}
			}
		}
	}
}

