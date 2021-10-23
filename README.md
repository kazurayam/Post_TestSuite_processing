Post Test Suite Processing --- how to zip the Report Folder in Katalon Studio
====

This is a small [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demonstration purpose.

You can download the zip at the [releases page](https://github.com/kazurayam/Post_TestSuite_processing/releases).

This project was developed using Katalon Studio v8.1.0 but is not dependent on the KS version.
It should work on every versions of KS.

This project is meant to propose a solution to the discussion raised in Katalon Forum at

- [How to zip the Report Folder](https://forum.katalon.com/t/how-to-zip-the-report-folder/58763)

## Demonstration

This project demonstrates how to make a zip file of Reports generated by a Test Suite execution.

Please download this project, open it with your local Katalon Studio, run a Test Suite Collection named `TSC`. It would demonstrate a few points, which would be interesing for those who want to *zip the Report Folder created by Test Suites*.

1. Open a Test Suite Collection named `Test Suites/TCS`. It comprises with 2 Test Suites.
  * `Test Suites/TS1`
  * `Test Suites/TS_post_TS1_processing`
1. Click the Execute button to run it. It will run for a few seconds. ![01_TSC](docs/images/01_TSC.png)
1. The `TSC` will run for a few seconds. *It will fail*. It fails intentionally for demostration purpose. Don't mind it.
1. Once `TSC` finished, a folder named `out` will be be created under the project directory. However, Katalon Studio has a bug; *the new `out` folder will not be displayed in the Test Explorer pane*. So, please close the project once and reopen it in order to let Katalon Studio be acknowled of the new `out` folder. ![02_out](docs/images/02_out_folder.png)
1. In the `out` folder, you will find two files created by the `TSC`. Here `yyyyMMdd_hhmmss` represent a varying timestamp.
  * `out/consume_TS_report.sh`
  * `out/Reports_yyyyMMdd_hhmmss.zip`
5. Please check the content of the `Reports_yyyyMMdd_hhmmss.zip` file using your favorite archiver tool. In the zip file, the contents of the `Reports/yyyyMMdd_hhmmss/TS1/yyyyMMdd_hhss2` folder is archived. It contains the test report in HTML/CSV/XML generated by a Test Suite named `Test Suites/TS1`. ![03_zip](docs/images/03_zip.png)
6. The `Test Suite/TS1` calls a Test Case `TC1`, which does nothing special, is as simple as this:
```
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment("TC1 started")
WebUI.comment("You can do whatever you like to here")
WebUI.comment("TC1 finsihed")
```
7. The Report of `Test Suite/TS1` were generated into the folder `Reports/yyyyMMdd_hhmmss/TS1/yyyyMMdd_hhss2`. And after `TS1`, another Test Suite `TS_post_TS1_processing` is executed. It makes the zip file at `out/Reports_yyyyMMdd_hhmmss.zip`.

### Caution

I used Mac, not Windows. This project assumes that `/bin/sh` command is available, which is usually true on Mac and Linux.

Windows users should edit the source to use `powershell.exe` instead of `/bin/sh`. The script file `out/consume_TS_report.sh` should rather be named as `out/consume_TS_report.ps1` and the script code should be rewriten in PowerShell.

Please help yourself revising this demo for Windows environment.

## Problem to solve

Katalon Studio users often want to make a zip file of the Reports of a Test Suite execution. If you don't mind doing it manually after the Test Suite finished, you can:

1. open the project folder using Windows Explorer
2. dig into the `<projectDir>/Reports/yyyyMMdd_hhmmss/testSuiteName/yyyyMMdd_hhmmss2` folder
3. right-click the folder and choose "zip it" menu.
4. You are done. You will get a zip file which contains HTML/CSV/XML reports.

But I don't like to do it manually. I want to **automate zipping it**. Not only creating the file, I want to transfer it to some remote storage for further processing.

How can I do it in my Katalon Studio project?

## Technical difficulties

I found 3 technical issues to overcome.

### Timing issue

When were the HTML/CSV/XML Reports of `TS1` generated?

### Variable path

The Report folder path includes variable timestamp yyyyMMddhh_mmss, not fixed

### How to run a Command from Groovy

In order to create a zip file of a folder contents, I want to use the good old bash shell commands: `zip`, `mv`, `cp`, `rm`, `mkdir`, `echo` etc. Also I want to use `curl` command to transfer files over network. I can create, by Groovy, a shell script file named `cmd.sh`. How can I execute a shell script `cmd.sh` with bash shell from my Groovy script?

## Proposed solution

### Running Shell Script using the subprocessj library

The `java.lang.ProcessBuilder` class enables running arbitrary commands in a new process from Groovy script. The following article covers how to use it.

- [How to run a Shell command in Java](https://www.baeldung.com/run-shell-command-in-java)

## Description
