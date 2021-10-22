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
			
			// Shell script file to be generated here
			Path commandFile = outDir.resolve(CommandGenerator.SH_FILENAME)
			
			// generate a shell script file which create a zip of Report, and try to POST it to a URL
			commandFile.text = CommandGenerator.doZipAndPost(testSuiteReportDir, archiveName, outDir)
						
			// do "chmod +x" to make the command file "executable" in the command line
			Subprocess subprocess = new Subprocess()
			CompletedProcess cp = subprocess.run(Arrays.asList("chmod", "+x", commandFile.toString()))
			cp.stdout().forEach { line -> println line }
			cp.stderr().forEach { line -> println line }
		}
	}
}