package com.cy.test.common;

import com.aventstack.extentreports.ExtentTest;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/22 15:00
 * <p>
 * Remark:
 */
public class MyReporter {
    public static ExtentTest report;
    private static String testName;

    public static String getTestName() {
        return testName;
    }

    public static void setTestName(String testName) {
        MyReporter.testName = testName;
    }
}
