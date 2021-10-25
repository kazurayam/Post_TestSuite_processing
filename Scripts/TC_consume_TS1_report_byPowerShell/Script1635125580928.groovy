import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kazurayam.ks.posttestsuiteprocessing.CommandGenerator

/**
 * This Test Case script calls pwsh (PowerShell) specifying "./out/consume_TS_report.ps1" file as script.
 *  * The file is prepared by the TestListner "PostTS1Processor".
 */
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path outDir = projectDir.resolve("out")
Path commandFile = projectDir.relativize(outDir.resolve(CommandGenerator.PWSH_FILENAME))

println "commandFile: ${commandFile.toString()}"

Subprocess subprocess = new Subprocess()
def cmd = Arrays.asList(getPwshBinaryPath(), "-c", commandFile.toString())
println "cmd = " + cmd.toString()
CompletedProcess cp = subprocess.run(cmd)

println "return code = " + cp.returncode()
cp.stdout().forEach { line -> println line }   // cp.stdout() returns a List<String> which contains messages from the /bin/sh
cp.stderr().forEach { line -> println line }

// if return code was not 0, then let this test case fail.
if (cp.returncode() != 0) {
	KeywordUtil.markFailedAndStop("${commandFile.toString()} failed, return code=${cp.returncode()}; ${stringify(cp.stderr())}")
}

/**
 * 
 */
String stringify(List<String> lines) {
	StringWriter sw = new StringWriter()
	lines.forEach({ line -> sw.println(line) })
	return sw.toString()
}

String getPwshBinaryPath() {
	if (isWindows()) {
		return "powershell.exe"
	} else {
		return "/usr/local/bin/pwsh"
	}
}

boolean isWindows() {
	return (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
}
