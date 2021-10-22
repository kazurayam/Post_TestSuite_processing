import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.subprocessj.Subprocess.CompletedProcess;
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

/**
 * This Test Case script calls /bin/sh (shell) specifying "./out/zipIt.sh" file as script.
 * The zipIt.sh files is prepared by the TestListner "TL".
 * The script in the zipIt.sh executes "zip" command to archive a directory created 
 * by preceding executio session of the Test Sute "TS1".
 */
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path outDir = projectDir.resolve("out")
Path commandFile = outDir.resolve("zipIt.sh")
Path commandFileRelativePath = projectDir.relativize(commandFile)

println "commandFileRelativePath: ${commandFileRelativePath.toString()}"

// make a new process in which we call "/bin/sh" specifyiing the command file  
Subprocess subprocess = new Subprocess()
def cmd = Arrays.asList("/bin/sh", "-c", commandFileRelativePath.toString())
println "cmd = " + cmd.toString()
CompletedProcess cp = subprocess.run(cmd)

println "return code = " + cp.returncode()
cp.stdout().forEach { line -> println line }   // cp.stdout() returns a List<String> which contains messages from the /bin/sh
cp.stderr().forEach { line -> println line }

// if return code was not 0, then let this test case fail.
if (cp.returncode() != 0) {
	KeywordUtil.markFailedAndStop("${commandFileRelativePath.toString()} failed, return code=${cp.returncode()}; ${stringify(cp.stderr())}")
}

/**
 * 
 */
String stringify(List<String> lines) {
	StringWriter sw = new StringWriter()
	lines.forEach({ line -> sw.println(line) })
	return sw.toString()
}
