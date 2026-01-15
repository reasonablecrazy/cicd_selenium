package naveennarayananacademy.pageobjectmodel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.Abstract_Components;

public class CheckOutPage extends Abstract_Components {
	
	WebDriver driver;
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement countryName;
	
	@FindBy(css="span[class='ng-star-inserted']")
	WebElement countryMouseOver;
	
	@FindBy(css=".btnn.action__submit.ng-star-inserted")
	WebElement placeOrderButton;
	
	@FindBy(xpath="//button/label")
	WebElement cartCount;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
	
	public CheckOutPage selectCountry(String country) {
		waitForElement(cartCount);
		System.out.println(cartCount.getText());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElement(countryName);
		Actions actions = new Actions(driver);
		actions.moveToElement(waitForElement(countryName))
		       .click()
		       .sendKeys(country)
		       .build()
		       .perform();
		//waitForElement(countryName).sendKeys("Canada");
		//getJS().executeScript("arguments[0].value='Canad'", countryName);
		waitForElement(countryMouseOver).click();
		return this;
	}

	public void submitCheckoutPage() {
		waitForElement(placeOrderButton);
		javascriptSubmit(placeOrderButton);
	}
}
