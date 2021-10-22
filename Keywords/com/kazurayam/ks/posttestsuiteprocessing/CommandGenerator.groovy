package com.kazurayam.ks.posttestsuiteprocessing

import java.nio.file.Path

public class CommandGenerator {

	public static String SH_FILENAME = "consume_TS_report.sh"

	static String doZipAndPost(Path testSuiteReportDir, String archiveName, Path outDir) {
		return """
zip ${archiveName} -r \"${testSuiteReportDir}\"
mv ${archiveName}.zip \"${outDir}/\"

# I know this would fail
curl -X POST https://localhost:80 -F 'file=@${outDir}/${archiveName}.zip'
"""
	}
}
