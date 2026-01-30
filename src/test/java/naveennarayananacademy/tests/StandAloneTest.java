package naveennarayananacademy.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

import naveennarayananacademy.pageobjectmodel.CheckOutPage;
import naveennarayananacademy.pageobjectmodel.ConfirmationPage;
import naveennarayananacademy.pageobjectmodel.LandingPage;
import naveennarayananacademy.pageobjectmodel.ProductCartPage;
import naveennarayananacademy.pageobjectmodel.RegistrationPage;
import naveennarayananacademy.testComponents.BaseTests;
import naveennarayananacademy.testComponents.RetryAnalyzer;

public class StandAloneTest extends BaseTests {
	
	@Test (groups= "E2E", retryAnalyzer = RetryAnalyzer.class)
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
	
	@Test (groups= "ErrorMsg", retryAnalyzer = RetryAnalyzer.class)
	public void incorrectLogin() {
		LandingPage lp = new LandingPage(getDriver());
		lp.login("twinklekhanna@gmail.com", "Test12345");
		lp.validateLoginErrorMessage();
	}
	
	@Test(groups="E2E", dataProvider = "getData", retryAnalyzer = RetryAnalyzer.class)
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
		return new Object[][] {{"twinklekhanna@gmail.com","Test1234","ADIDAS ORIGINAL","Canada"}
			};
			//{"dimplekapadia@gmail.com","Test12345","ADIDAS ORIGINAL","Canada"}
	}
	
	@DataProvider(name = "loginData",parallel = false)
	public Object[][] getDataForLogin() throws IOException {
		Path path = Paths.get(System.
				getProperty("user.dir"), "src","test","java","naveennarayananacademy","testComponents","TestData.JSON");
		List<HashMap<String,String>> ls = extractDataFromJSON(path);
		
	    return new Object[][] {
	        { ls.get(0) }   // second test run
	    };
	}
	
	@Test(groups="E2E", dataProvider = "loginData", retryAnalyzer = RetryAnalyzer.class)
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
		
		System.out.println("Successfully concluded the test");
	}
	
	@DataProvider(name = "registrationData", parallel = false)
    public Object[][] getRegistrationData() {
        String randomEmail1 = "twinklekhanna+reg_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
        String randomEmail2 = "dimplekapadia+reg_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
        return new Object[][] {
            {"Twinkle", "Khanna", randomEmail1, "9876543210", "Doctor", "Female", "Test@1234", "Test@1234"},
            {"Dimple", "Kapadia", randomEmail2, "9123456780", "Engineer", "Male", "Test@5678", "Test@5678"}
        };
    }

    @Test(groups="Registration", dataProvider = "registrationData", retryAnalyzer = RetryAnalyzer.class)
    public void completeRegistration(String firstName, String lastName, String email, String phone, String occupation, String gender, String password, String confirmPassword) {
        RegistrationPage regPage = new RegistrationPage(getDriver());
        regPage.fillRegistrationForm(firstName, lastName, email, phone, occupation, gender, password, confirmPassword)
                .checkAgeConfirmation();
        regPage.clickRegister();
        // Assert registration success message or toast
        boolean isSuccess = regPage.isAccountCreatedSuccessMessageDisplayed();
        org.testng.Assert.assertTrue(isSuccess,
                "Registration was not successful. Success message or toast not found.");
    }

}