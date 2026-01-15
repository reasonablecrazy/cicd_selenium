package naveennarayananacademy.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import naveennarayananacademy.testComponents.BaseTests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @After
    public void tearDown(Scenario scenario) {

    	if (scenario.isFailed()) {
            try {
            	
            	WebDriver driver = BaseTests.getDriver();
                // Take screenshot bytes
                byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // Attach to Cucumber (works for Extent)
                scenario.attach(src, "image/jpeg", scenario.getName());

                // Save physical file for Extent to pick up
                File screenshotDir = new File("target/ExtentReport/screenshots/");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(
                        new File(screenshotDir, scenario.getName().replaceAll("[^a-zA-Z0-9]", "_") + ".png"));
                fos.write(src);
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}