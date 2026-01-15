package naveennarayananacademy.pageobjectmodel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import abstractComponents.Abstract_Components;

public class ConfirmationPage extends Abstract_Components {
	
	WebDriver driver;
	
	@FindBy(xpath="//h1[contains(text(),'Thankyou')]")
	WebElement confPageThankYouText;	
	
	@FindBy(xpath="//td/div[1]")
	List <WebElement> listProducts;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	    PageFactory.initElements(driver, this);
	}

	public ConfirmationPage validateTheThankYouMessage() {
		waitForElement(confPageThankYouText);
		System.out.println(confPageThankYouText.getText().trim());
		Assert.assertTrue(confPageThankYouText.getText().trim().equalsIgnoreCase("Thankyou for the order.")
				,"Expected: Thankyou for the order. But Actual: "+confPageThankYouText.getText());
		return this;
	}

	public void validateProductOnFinalPage(String string) {
		waitForElement(confPageThankYouText);
		System.out.println(confPageThankYouText.getText().trim());
		boolean productFound = waitForElementsList(listProducts).stream().
				anyMatch(p->p.getText().equalsIgnoreCase(string));
		Assert.assertTrue(productFound, "Product not found"+string);
	}
}
