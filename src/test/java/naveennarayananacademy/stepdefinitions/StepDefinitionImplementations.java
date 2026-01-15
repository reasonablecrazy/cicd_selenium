package naveennarayananacademy.stepdefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import naveennarayananacademy.pageobjectmodel.CheckOutPage;
import naveennarayananacademy.pageobjectmodel.ConfirmationPage;
import naveennarayananacademy.pageobjectmodel.LandingPage;
import naveennarayananacademy.pageobjectmodel.ProductCartPage;
import naveennarayananacademy.testComponents.BaseTests;

public class StepDefinitionImplementations extends BaseTests {
	
	public LandingPage lp;
	public ProductCartPage prodCartPage;
	public CheckOutPage checkoutPage;
	public ConfirmationPage confPage;
	
	@Given("I land on eCommerce page")
	public void I_land_on_eCommerce_page() throws IOException {
		initializeDriver();
		lp = new LandingPage(getDriver());
		lp.loadThePage();
	}
	
	@Given("^I logged in with the username (.*) and password (.+) and click on 'Submit' button$")
	public void Login_with_the_username_and_password_and_click_on_Submit_button(String userName,String password) {
		lp.login(userName, password);
	}
	
	@When("^I add the product name (.*) to the cart$")
	public void Add_the_product_to_the_cart(String productName){
		prodCartPage = new ProductCartPage(getDriver());
		prodCartPage.getProductList().addProductsToCart(productName).navigateToCart().
			validateProductsOnCartPage().submitCart();
	}
	
	@When("^I enter the country as (.*) and submit the Checkout page$")
	public void enter_the_country_and_submit_the_Checkout_page(String countryName) {
		checkoutPage = new CheckOutPage(getDriver());
		checkoutPage.selectCountry(countryName).submitCheckoutPage();
	}
	
	@Then("^order confirmation page is displayes as expected and (.*) is present on the confirmation page$")
	public void order_confirmation_page_validation(String productName) {
		confPage = new ConfirmationPage(getDriver());
		confPage.validateProductOnFinalPage(productName);
		getDriver().quit();
	}
	
	@But("incorrect login error message is displayed")
	public void incorrect_login_error_message_is_displayed() {
		lp.validateLoginErrorMessage();
		System.out.println("EXTENT PATH -> " + System.getProperty("extent.reporter.spark.out"));
		getDriver().quit();
	}
}
