package naveennarayananacademy.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

import naveennarayananacademy.pageobjectmodel.CheckOutPage;
import naveennarayananacademy.pageobjectmodel.ConfirmationPage;
import naveennarayananacademy.pageobjectmodel.LandingPage;
import naveennarayananacademy.pageobjectmodel.ProductCartPage;
import naveennarayananacademy.testComponents.BaseTests;
import naveennarayananacademy.testComponents.RetryAnalyzer;

public class StandAloneTest extends BaseTests {
	
	@Test (groups= "E2E")
	public void submitTest() {
		
		LandingPage lp = new LandingPage(getDriver());
		lp.login("twinklekhanna@gmail.com", "Test1234");
		
		ProductCartPage prodCartPage = new ProductCartPage(getDriver());
		prodCartPage.getProductList().addProductsToCart("ADIDAS").
			addProductsToCart("ZARA").navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(getDriver());
		checkoutPage.selectCountry("Canada").submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(getDriver());
		confPage.validateTheThankYouMessage();		
	}
	
	@Test (groups= "ErrorMsg")
	public void incorrectLogin() {
		LandingPage lp = new LandingPage(getDriver());
		lp.login("twinklekhanna@gmail.com", "Test12345");
		lp.validateLoginErrorMessage();
	}
	
	@Test(groups="E2E", dataProvider = "getData")
	public void validateProductOnConfirmationPage(String username, String password, String pname,String countryName) {
		LandingPage lp = new LandingPage(getDriver());
		lp.login(username, password);
		
		ProductCartPage prodCartPage = new ProductCartPage(getDriver());
		prodCartPage.getProductList().addProductsToCart(pname).navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(getDriver());
		checkoutPage.selectCountry(countryName).submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(getDriver());
		confPage.validateProductOnFinalPage(pname);	
	}
	
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"twinklekhanna@gmail.com","Test1234","ADIDAS ORIGINAL","Canada"},
			{"dimplekapadia@gmail.com","Test12345","ADIDAS ORIGINAL","Canada"}};
	}
	
	@DataProvider(name = "loginData",parallel = false)
	public Object[][] getDataForLogin() throws IOException {
		List<HashMap<String,String>> ls = extractDataFromJSON(System.
				getProperty("user.dir")+"//src//test//java//naveennarayananacademy//testComponents//TestData.JSON");
		return new Object[][] {
			{ls.get(0)}
		};
	}
	
	@Test(groups="E2E", dataProvider = "loginData")
	public void validateProductOnConfirmationPage_with_JSON(HashMap <String,String> data) {
		LandingPage lp = new LandingPage(getDriver());
		lp.login(data.get("username"), data.get("password"));
		
		ProductCartPage prodCartPage = new ProductCartPage(getDriver());
		prodCartPage.getProductList().addProductsToCart(data.get("prodname")).navigateToCart().
			validateProductsOnCartPage().submitCart();
		
		CheckOutPage checkoutPage = new CheckOutPage(getDriver());
		checkoutPage.selectCountry(data.get("country")).submitCheckoutPage();
		
		ConfirmationPage confPage = new ConfirmationPage(getDriver());
		confPage.validateProductOnFinalPage(data.get("prodname"));	
	}

}
