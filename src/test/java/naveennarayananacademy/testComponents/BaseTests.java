package naveennarayananacademy.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import naveennarayananacademy.pageobjectmodel.LandingPage;

public class BaseTests {
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }
	
	public static WebDriver initializeDriver(String browserNameFromTest) throws IOException{
		

		String fullDir = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator
				+"naveennarayananacademy"+File.separator+"resources"+File.separator+"GlobalData.properties";
		FileInputStream file = new FileInputStream(fullDir);
		
		
		Properties prop = new Properties();
		prop.load(file);
		String browserName = (System.getProperty("browser")!=null) ? 
				System.getProperty("browser") : browserNameFromTest;
		System.out.println(browserName);
		String chromeMode = prop.getProperty("chrome-mode","not-headless");
		ChromeOptions options= new ChromeOptions();
		if(chromeMode.equalsIgnoreCase("headless")){
			options.addArguments("--headless=new");
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");
		}
		
		String modeOfExecution = System.getProperty("execution")!= null ? System.getProperty("execution") : 
			prop.getProperty("execution","local");
		
		if(modeOfExecution.equalsIgnoreCase("local")) {
			if(browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver(options));
			}
			else if(browserName.equalsIgnoreCase("safari")){
				WebDriverManager.safaridriver().setup();
				driver.set(new SafariDriver());
			}
			else if(browserName.equalsIgnoreCase("firefox")){
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
			}
			else {
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
			}
		}
		else {
			try {
		        URL gridUrl = new URL(prop.getProperty("grid.url"));
		        MutableCapabilities options2;

		        switch (browserName.toLowerCase()) {
		            case "chrome":
		                options2 = new ChromeOptions();
		                break;
		            case "firefox":
		                options2 = new FirefoxOptions();
		                break;
		            default:
		                throw new RuntimeException("Unsupported browser: " + browserName);
		        }

		        driver.set(new RemoteWebDriver(gridUrl, options));
		        return getDriver();

		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }
		}
						
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();
	}

	@Parameters("browser")
	@BeforeMethod (alwaysRun=true)
	public static void launchApplication(String browserName) throws IOException {
		initializeDriver(browserName);
		LandingPage lp = new LandingPage(getDriver());
		lp.loadThePage();
	}
	
	@AfterMethod (alwaysRun=true)
	public void quitDriver() {
		getDriver().quit();
		driver.remove();
	}
	
	public List<HashMap<String,String>> extractDataFromJSON(Path path) throws IOException {
		
		String jsonContent = Files.readString(path);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> ls = mapper.readValue(jsonContent, new TypeReference <List<HashMap<String,String>>>(){});
		
		return ls;
		
	}
	
	public String getScreenshot(String methodName) throws IOException {
		File f = ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(System.getProperty("user.dir")+File.separator+"Reports"+File.separator+
				methodName+".JPEG"));
		String filePath = System.getProperty("user.dir")+File.separator+"Reports"+
				methodName+".JPEG";
		return filePath;
	}

}
