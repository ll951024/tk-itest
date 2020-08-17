package com.cy.test.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;
import com.cy.test.common.ExtentReportsContext;
import com.mysql.jdbc.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestNGIReporter implements IReporter {

	List<String> groups = new ArrayList<String>();

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				ExtentTest parent = ExtentReportsContext.getInstance().createTest(context.getSuite().getName());
				ExtentReportsContext.parentTest.set(parent);

				buildTestNodes(context.getFailedTests(), Status.FAIL);
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
				buildTestNodes(context.getPassedTests(), Status.PASS);

				for (String group : groups) {
					ExtentReportsContext.parentTest.get().assignCategory(group);
					ExtentReportsContext.getInstance().flush();
				}
				groups.clear();
			}
		}

		for (String s : org.testng.Reporter.getOutput()) {
			ExtentReportsContext.getInstance().setTestRunnerOutput(s);
		}
	}

	private void buildTestNodes(IResultMap tests, Status status) {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {

				ExtentTest child = ExtentReportsContext.parentTest.get().createNode(result.getMethod().getMethodName());
				ExtentReportsContext.test.set(child);

				String groupsStr = "";
				for (String group : result.getMethod().getGroups()) {
					if (!groups.contains(group)) {
						groups.add(group);
					}

					if (!StringUtils.isNullOrEmpty(groupsStr)) {
						groupsStr += "|";
					}
					groupsStr += group;

					ExtentReportsContext.test.get().assignCategory(group);
				}

				if (!StringUtils.isNullOrEmpty(result.getMethod().getDescription())) {
					ExtentReportsContext.test.get().log(Status.PASS,
							String.format("用例描述:%s Priority:%s 分组:%s", result.getMethod().getDescription(),
									Integer.toString(result.getMethod().getPriority()), groupsStr));
				}

				if (result.getParameters().length > 0) {
					for (int i = 0; i < result.getParameters().length; i++) {
						ExtentReportsContext.test.get().log(Status.PASS, "用例参数列表:");
						ExtentReportsContext.test.get().log(Status.PASS, String.format("第%d个参数:", i + 1));
						ExtentReportsContext.test.get().log(Status.PASS,
								JSONObject.toJSONString(result.getParameters()[i]));
					}
				}

				ExtentReportsContext.test.get().log(Status.PASS,
						String.format("=============Run: %s===============", result.getMethod()));

				List<String> outputs = Reporter.getOutput(result);
				if (outputs != null) {
					for (String output : outputs) {
						ExtentReportsContext.test.get().log(Status.PASS, output);
					}
				}

				if (result.getThrowable() != null) {
					ExtentReportsContext.test.get().log(status, result.getThrowable());
				}

				ExtentReportsContext.test.get().getModel().setStartTime(getTime(result.getStartMillis()));
				ExtentReportsContext.test.get().getModel().setEndTime(getTime(result.getEndMillis()));
			}

			ExtentReportsContext.getInstance().flush();
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
