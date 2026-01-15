package naveennarayananacademy.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import naveennarayananacademy.pageobjectmodel.LandingPage;

public class BaseTests {
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }
	
	public static WebDriver initializeDriver() throws IOException{
		

		String defaultDir = System.getProperty("user.dir");
		String path = "//src//main//java//naveennarayananacademy//resources//GlobalData.properties";
		String fullDir = defaultDir+path;
		FileInputStream file = new FileInputStream(fullDir);
		
		Properties prop = new Properties();
		prop.load(file);
		String browserName = (System.getProperty("browser")!=null) ? 
				System.getProperty("browser") : prop.getProperty("browser");
		System.out.println(browserName);
		String chromeMode = prop.getProperty("chrome-mode","not-headless");
		ChromeOptions options= new ChromeOptions();
		if(chromeMode.equalsIgnoreCase("headless")){
			options.addArguments("--headless=new");
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");
		}
						
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver(options));
		}
		else if(browserName.equalsIgnoreCase("safari")){
			WebDriverManager.safaridriver().setup();
			driver.set(new SafariDriver());
		}
		else {
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		}
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();
	}
	
	@BeforeMethod (alwaysRun=true)
	public static void launchApplication() throws IOException {
		initializeDriver();
		LandingPage lp = new LandingPage(getDriver());
		lp.loadThePage();
	}
	
	@AfterMethod (alwaysRun=true)
	public void quitDriver() {
		getDriver().quit();
		driver.remove();
	}
	
	public List<HashMap<String,String>> extractDataFromJSON(String filepath) throws IOException {
		
		String jsonContent = Files.readString(new File(filepath).toPath());
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> ls = mapper.readValue(jsonContent, new TypeReference <List<HashMap<String,String>>>(){});
		
		return ls;
		
	}
	
	public String getScreenshot(String methodName) throws IOException {
		File f = ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(System.getProperty("user.dir")+"//Reports//"+
				methodName+".JPEG"));
		String filePath = System.getProperty("user.dir")+"//Reports//"+
				methodName+".JPEG";
		return filePath;
	}

}
