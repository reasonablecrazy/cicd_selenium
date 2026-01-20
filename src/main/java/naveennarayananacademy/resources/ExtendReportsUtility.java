package naveennarayananacademy.resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportsUtility {
		
	public static ExtentReports generateExtentReport() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String timestamp = formatter.format(now);
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir")+
				File.separator+"Reports"+File.separator+"AutomationReport"+File.separator+timestamp+".html");
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		ExtentReports extent = new ExtentReports();
	    extent.attachReporter(reporter);
	    extent.setSystemInfo("SDET", "Naveen");	
	    return extent;
	}
}
