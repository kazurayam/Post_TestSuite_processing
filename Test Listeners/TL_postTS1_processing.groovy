import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestSuiteContext
import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;

class TL_postTS1_processing {
	
	// the project's home directoory
	Path projectDir
	
	// work directory where the command file and the zip files are stored
	Path outDir
	
	// name of the command file
	static final String COMMAND_FILE_NAME = "zipIt.sh"
	 
	TL_postTS1_processing() {
		projectDir = Paths.get(RunConfiguration.getProjectDir())
		outDir = projectDir.resolve("out")
		// create the outDir
		Files.createDirectories(outDir)
	}
	
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		// only when TS1 was executed
		if (testSuiteContext.getTestSuiteId() == 'Test Suites/TS1') {
			// create the command file
			Path tsReportDir = projectDir.relativize(Paths.get(RunConfiguration.getReportFolder()))
			String timestamp = tsReportDir.getFileName()
			Path commandFile = outDir.resolve(COMMAND_FILE_NAME)
			
			// stuff the command file with shell script
			// here I used bash script,
			// but you can use whatever scripting engine you like
			// e.g, Powershell, Python, Groovy, etc
			commandFile.text = """
zip Reports_${timestamp} -r \"${tsReportDir}\"
mv Reports_${timestamp}.zip \"${outDir}/\"
"""
			
			// do "chmod +x" to make the command file "executable" in the command line
			Subprocess subprocess = new Subprocess()
			CompletedProcess cp = subprocess.run(Arrays.asList("chmod", "+x", commandFile.toString()))
			cp.stdout().forEach { line -> println line }
			cp.stderr().forEach { line -> println line }
		}
	}
}