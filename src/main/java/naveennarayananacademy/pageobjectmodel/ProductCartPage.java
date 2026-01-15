package naveennarayananacademy.pageobjectmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import abstractComponents.Abstract_Components;

public class ProductCartPage extends Abstract_Components {
	
	WebDriver driver;
	
	@FindBy(xpath="//h5/b")
	List <WebElement> products_list;
	
	@FindBy(id="toast-container")
	WebElement toastContainer;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	WebElement cartLink;
	
	@FindBy(xpath="//div[@class='cartSection']")
	WebElement cartSection;
	
	@FindBy(css="li.totalRow button")
	WebElement cartSubmitButton;
	
	String [] pName_Exp_text = new String[4];
	int i=0;
	
	public ProductCartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
	
	public ProductCartPage getProductList() {
		getWait().until(ExpectedConditions.visibilityOfAllElements(products_list));
		return this;
	}

	public ProductCartPage addProductsToCart(String prodName) {
		// TODO Auto-generated method stub
		WebElement pName = products_list.stream().filter(p-> p.getText().contains(prodName)).findFirst().orElse(null);
		pName_Exp_text[i] = pName.getText();
		WebElement ele_pName_Exp_adidas = driver.findElement(By.
				xpath("//b[text()='"+pName_Exp_text[i]+"']//parent::h5//following-sibling::button[2]"));
		
		waitForElement(ele_pName_Exp_adidas);javascriptSubmit(ele_pName_Exp_adidas);
		getWait().until(ExpectedConditions.visibilityOf(toastContainer));
		getWait().until(ExpectedConditions.invisibilityOf(toastContainer));
		i++;

		return this;
	}

	public ProductCartPage navigateToCart() {
		waitForElement(cartLink);javascriptSubmit(cartLink);
		waitForElement(cartSection);
		return this;
	}

	public void submitCart() {
		waitForElement(cartSubmitButton);
		javascriptSubmit(cartSubmitButton);
	}

	public ProductCartPage validateProductsOnCartPage() {
		List <WebElement> items_in_cart = driver.findElements(By.xpath("//div[@class='cartSection']//h3"));
		List<String> item_present_adidas = items_in_cart.stream().map(p->p.getText())
				.collect(Collectors.toList());
		System.out.println(item_present_adidas);
		for(int i=0;i<item_present_adidas.size();i++) {
			Assert.assertEquals(item_present_adidas.get(i), pName_Exp_text[i], "Expected :"+
					pName_Exp_text[i]+". But found: "+item_present_adidas.get(0));
		}
		
		return this;
	}
	
}
