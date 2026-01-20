package naveennarayananacademy.testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import naveennarayananacademy.resources.ExtendReportsUtility;

public class Listeners implements ITestListener {

    private static ExtentReports extentReport =
            ExtendReportsUtility.generateExtentReport();

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReport.createTest(result.getMethod().getMethodName());
        extentTest.set(test);

        test.assignCategory("Regression");
        test.assignAuthor("Naveen");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getExtentTest().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = getExtentTest();
        if (test == null) {
            return; // safety guard
        }

        test.fail(result.getThrowable());

        try {
            WebDriver driver = BaseTests.getDriver();
            if (driver != null) {
                BaseTests base = new BaseTests();
                String path = base.getScreenshot(result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getExtentTest().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
    }
}