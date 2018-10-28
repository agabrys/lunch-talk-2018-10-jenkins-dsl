import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import jenkins.model.Jenkins

def sourceDir = Paths.get("$WORKSPACE/configuration")
def destinationDir = Paths.get(Jenkins.get().root.toString())

def restartRequired = false

Files.walk(sourceDir).filter{ Files.isRegularFile(it) }.each{
	def destFile = destinationDir.resolve(sourceDir.relativize(it))

	def overwrite = true
	if (Files.exists(destFile)) {
		def messageDigest = MessageDigest.getInstance("MD5")
		def srcHash = messageDigest.digest(Files.readAllBytes(it))
		def destHash = messageDigest.digest(Files.readAllBytes(destFile))
		overwrite = !Arrays.equals(srcHash, destHash)
	}

	if (overwrite) {
		Files.copy(it, destFile, StandardCopyOption.REPLACE_EXISTING)
		restartRequired = true
	}
}

if (restartRequired) {
	Jenkins.get().safeRestart()
}
