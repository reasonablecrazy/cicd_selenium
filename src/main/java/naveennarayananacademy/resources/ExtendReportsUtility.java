package naveennarayananacademy.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportsUtility {
		
	public static ExtentReports generateExtentReport() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd_hhmmss");
        String timestamp = formatter.format(now);
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir")+
				"//Reports//AutomationReport-"+timestamp+".html");
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		ExtentReports extent = new ExtentReports();
	    extent.attachReporter(reporter);
	    extent.setSystemInfo("SDET", "Naveen");	
	    return extent;
	}
}
