import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry
import java.nio.file.Files
import java.nio.file.Paths
import jenkins.model.Jenkins

def cspStorage = Jenkins.get().root.toString() + File.separator + 'csp-storage'
Files.createDirectories(Paths.get(cspStorage))

def environmentVariables = new EnvironmentVariablesNodeProperty(
	new Entry('PRODUCT_REPOSITORIES', 'https://github.com/gabrysbiz/lesscss-maven-plugin.git,https://github.com/gabrysbiz/css-splitter-maven-plugin.git,https://github.com/gabrysbiz/directory-content-maven-plugin.git'),
	new Entry('PRODUCT_REPOSITORY_DEFAULT', 'https://github.com/gabrysbiz/css-splitter-maven-plugin.git'),
	new Entry('CSP_STORAGE', cspStorage)
)

Jenkins.get().globalNodeProperties.replace(environmentVariables)
Jenkins.get().save()
