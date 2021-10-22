import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestSuiteContext

class TS1ReportDirLister {

	Path report_dir
	
	TS1ReportDirLister() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		report_dir = Paths.get(RunConfiguration.getReportFolder())
		report_dir = projectDir.relativize(report_dir)
	}
		
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		if (testSuiteContext.getTestSuiteId() == "Test Suites/TS1") {
			Subprocess subprocess = new Subprocess()
			//subprocess.cwd(new File(System.getProperty("user.home")))
			List<String> cmd = Arrays.asList("ls", "-la", "./" + report_dir.toString())
			CompletedProcess cp = subprocess.run(cmd)
			println "TS1ReportDirLister output ----------------------------------------------"
			println "executing: " + cmd.join(" ")
			println "return code = " + cp.returncode()
			cp.stdout().forEach { line -> println line }
			cp.stderr().forEach { line -> println line }
		}
	}
}