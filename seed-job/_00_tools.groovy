import hudson.model.JDK
import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation
import hudson.tools.InstallSourceProperty
import hudson.tools.JDKInstaller
import hudson.tools.ZipExtractionInstaller
import jenkins.model.Jenkins

Jenkins.get().setLabelString(System.getProperty("os.name").contains('Windows') ? 'windows' : 'linux')

def jdk11Windows = new ZipExtractionInstaller('windows', 'https://github.com/SAP/SapMachine/releases/download/sapmachine-11.0.1%2B13-0/sapmachine-jdk-11.0.1.13_windows-x64_bin.zip', 'sapmachine-11.0.1')
def jdk11Linux = new ZipExtractionInstaller('linux', 'https://github.com/SAP/SapMachine/releases/download/sapmachine-11.0.1%2B13-0/sapmachine-jdk-11.0.1.13_linux-x64_bin.tar.gz', 'sapmachine-11.0.1')
def jdk11 = new JDK('11', '', Arrays.asList(new InstallSourceProperty(Arrays.asList(jdk11Windows, jdk11Linux))))

def jdk8 = new JDK('8', '', Arrays.asList(new InstallSourceProperty(Arrays.asList(new JDKInstaller('jdk-8u192-oth-JPR', true)))))

Jenkins.get().setJDKs(Arrays.asList(jdk11, jdk8))

def mavenInstaller = new ZipExtractionInstaller('', 'http://ftp.man.poznan.pl/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.zip', 'apache-maven-3.5.4')
def maven = new MavenInstallation('3.5.4', '', Arrays.asList(new InstallSourceProperty(Arrays.asList(mavenInstaller))))
Jenkins.get().getDescriptorByType(Maven.DescriptorImpl.class).setInstallations(maven)

Jenkins.get().save()
