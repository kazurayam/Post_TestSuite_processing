package com.kazurayam.ks.posttestsuiteprocessing

import java.nio.file.Path

public class CommandGenerator {

	public static String SH_FILENAME = "consume_TS_report.sh"
	public static String PWSH_FILENAME = "consume_TS_report.ps1"

	static String sh_zipAndPost(Path testSuiteReportDir, String archiveName, Path outDir) {
		return """
zip ${archiveName} -r \"${testSuiteReportDir}\"
mv ${archiveName}.zip \"${outDir}/\"
echo \"output: ${outDir}/${archiveName}.zip\"

# I know this would fail
curl -X POST https://localhost:80 -F 'file=@${outDir}/${archiveName}.zip'
"""
	}

	static String pwsh_zip(Path testSuiteReportDir, String archiveName, Path outDir) {
		return """
Compress-Archive -Path \"${testSuiteReportDir}\" -DestinationPath \"${outDir}/${archiveName}.zip\"
Write-Output \"output: ${outDir}/${archiveName}.zip\"
"""
	}
}
