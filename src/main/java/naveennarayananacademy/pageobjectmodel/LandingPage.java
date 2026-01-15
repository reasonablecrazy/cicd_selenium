package naveennarayananacademy.pageobjectmodel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import abstractComponents.Abstract_Components;

public class LandingPage extends Abstract_Components {
	
	WebDriver driver;
	
	@FindBy(id="userEmail")
	WebElement userMail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement loginButton;
	
	@FindBy(id="toast-container")
	WebElement toastContainerErrorMessage;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
	
	public void login(String email, String password) {
		waitForElement(userMail).sendKeys(email);
		waitForElement(userPassword).sendKeys(password);
		waitForElement(loginButton).click();
	}
	
	public LandingPage loadThePage() {
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");
		return this;
	}
	
	public void validateLoginErrorMessage() {
		//waitForElement(loginButton).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String errorMessage = driver.switchTo().alert().getText();
		String errorMessage = waitForElementToBeVisible(toastContainerErrorMessage).getText();
		System.out.println(errorMessage);
		String expErrorMessage= "Incorrect email or password.";
		Assert.assertTrue(errorMessage.equalsIgnoreCase(expErrorMessage),
				"Expected: "+expErrorMessage+" Actual: "+errorMessage);
	}
}
