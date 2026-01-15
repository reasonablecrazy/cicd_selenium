package cucumberTests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/cucumberTests", 
glue={"naveennarayananacademy.stepdefinitions",
        "naveennarayananacademy.hooks"},
monochrome=true, plugin = {
        "pretty",
        "html:target/cucumber.html",
        "json:target/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // <--- note trailing colon
    })
public class TestNG_TestRunner extends AbstractTestNGCucumberTests {

}
	