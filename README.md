How to zip the Report folder created by a Test Suite in Katalon Stdio
====

This is a small Katalon Studio project for demonstration purpose.
You can donwload the zip, unarchive it and open it with your local Katalon Studio.

This project was developed using Katalon Studio v8.1.0 but is not version dependent.
It should work on every versions of KS.

This project is meant to propose a solution to the discussion raised in Katalon Forum at

- [How to zip the Report Folder](https://forum.katalon.com/t/how-to-zip-the-report-folder/58763)

## Problem to solve

Katalon Studio users often want to make a zip file of the Report created when they execute 
a Test Suite. It is easily accomplished manually after the Test Suite has finished. Just open
the project folder using Windows Explorer; dig into the `<projectDir>/Reports/yyyyMMdd_hhmmss/testSuiteName/yyyyMMdd_hhmmss' folder; right-click the folder and choose "zip it" menu. You are done. You will get a zip file. But they want to automate this zipping process. They sometimes want to do more.

1. they want to create a zip file
2. they want to transfer the zip file to somewhere via E-Mail, or to shared File server, etc.

How can they automate such post-test processing as a part of the test suite(s) in Katlaon Studio?

You will encounter 2 technical challenges.

### Timing problem when the Resport is generated



### How to execute commandline script from Katlaon scripts (Test Cases, Test Listener)

## Proposed solution

## Description

## Issues 

In this demo project, I used Mac osX, not Windows. I targeted "bash" command-line interpreter, which is not avaiable on Windows as default.

If you want a solution for Windoows, you can create your own by rewriting this project targeting **PowerShell** in mind. It should work. PowerShell is generally available in Windows10.

