package abstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Abstract_Components {
	
	WebDriver driver;
	private WebDriverWait wait;
	JavascriptExecutor js;

	public Abstract_Components(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public WebElement waitForElement(WebElement element) {
		getWait().until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	
	public WebElement waitForElementToBeVisible(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}
	
	public List<WebElement> waitForElementsList(List <WebElement> elements) {
		getWait().until(ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}
	
	public void javascriptSubmit(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public JavascriptExecutor getJS() {
		return js;
	}
	
	//public void waitForElementss(By findBy)
}
