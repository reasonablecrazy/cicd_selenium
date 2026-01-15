package naveennarayananacademy.testComponents;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import naveennarayananacademy.resources.ExtendReportsUtility;

public class Listeners extends BaseTests implements ITestListener {
	
	ExtentReports extentReport = ExtendReportsUtility.generateExtentReport();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	ExtentTest test;
	
	void setExtentTest(ExtentTest test) {
		extentTest.set(test);
	}
	ExtentTest getExtentTest() {
		return extentTest.get();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extentReport.createTest(result.getMethod().getMethodName());
		setExtentTest(test);
		getExtentTest().assignCategory("Regression");
		getExtentTest().assignAuthor("Naveen");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getExtentTest().pass("The test has passed: "+result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getExtentTest().skip("The test is skipped: "+result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		Object[]parameters = result.getParameters();
		String parameter = parameters[0].toString();
		System.out.println("The test with parameters: "+parameter+" has failed.");
		getExtentTest().fail("The test has failed: "+result.getMethod().getMethodName()+
				result.getThrowable());
		String testName = result.getMethod().getMethodName();
		String filePath = null;
		try {
			filePath = getScreenshot(testName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		getExtentTest().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extentReport.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Object[]parameters = result.getParameters();
		String parameter = parameters[0].toString();
		System.out.println("The test with parameters: "+parameter+" has failed.");
		getExtentTest().fail("The test : "+result.getMethod().getMethodName()+" ran with "
				+ "parameters: "+parameter+" has failed");
		getExtentTest().fail(result.getThrowable());
		String testName = result.getMethod().getMethodName();
		String filePath = null;
		try {
			filePath = getScreenshot(testName);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		getExtentTest().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

}
