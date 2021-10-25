import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestSuiteContext
import com.kazurayam.subprocessj.Subprocess
import com.kazurayam.subprocessj.Subprocess.CompletedProcess
import com.kazurayam.ks.posttestsuiteprocessing.CommandGenerator

class PostTS1Processor {
	
	// the project's home directory
	Path projectDir
	
	// work directory where the command file and the zip files are stored
	Path outDir
	 
	PostTS1Processor() {
		projectDir = Paths.get(RunConfiguration.getProjectDir())
		outDir = projectDir.relativize(projectDir.resolve("out"))
		// create the outDir
		Files.createDirectories(outDir)
	}
	
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		// only when TS1 was executed
		if (testSuiteContext.getTestSuiteId() == 'Test Suites/TS1') {
			// create the command file
			Path testSuiteReportDir = projectDir.relativize(Paths.get(RunConfiguration.getReportFolder()))
			String timestamp = testSuiteReportDir.getFileName()
			String archiveName = "Reports_" + timestamp
			
			// generate Bash shell script file
			Path shFile = outDir.resolve(CommandGenerator.SH_FILENAME)
			// generate a sh script text, which create a zip of Report, and try to POST it to a URL
			String sh = CommandGenerator.sh_zipAndPost(testSuiteReportDir, archiveName, outDir)
			// write the sh script into the file
			shFile.text = sh
			
			// generate PowerShell script file
			Path ps1File = outDir.resolve(CommandGenerator.PWSH_FILENAME)
			String ps1 = CommandGenerator.pwsh_zip(testSuiteReportDir, archiveName, outDir)
			ps1File.text = ps1
		}
	}
	
}