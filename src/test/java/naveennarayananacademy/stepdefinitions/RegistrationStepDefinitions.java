package naveennarayananacademy.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import naveennarayananacademy.pageobjectmodel.RegistrationPage;
import naveennarayananacademy.testComponents.BaseTests;
import org.testng.Assert;
import java.util.UUID;

public class RegistrationStepDefinitions extends BaseTests {
    public RegistrationPage registrationPage;
    public String generatedEmail;

    @Given("I am on the registration page on {string} browser")
    public void i_am_on_the_registration_page(String browserName) throws Exception {
        initializeDriver(browserName);
        registrationPage = new RegistrationPage(getDriver());
        getDriver().get("https://rahulshettyacademy.com/client/#/auth/register");
    }

    @And("I fill the registration form with valid details")
    public void i_fill_the_registration_form_with_valid_details() {
        generatedEmail = "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
        registrationPage.fillRegistrationForm(
                "TestFirst", "TestLast", generatedEmail, "9876543210", "Doctor", "Female", "Test@1234", "Test@1234"
        ).checkAgeConfirmation();
    }

    @And("I submit the registration form")
    public void i_submit_the_registration_form() {
        registrationPage.clickRegister();
    }

    @Then("the account should be created successfully")
    public void the_account_should_be_created_successfully() {
        boolean isSuccess = registrationPage.isAccountCreatedSuccessMessageDisplayed();
        Assert.assertTrue(isSuccess, "Registration was not successful. Success message not found.");
        getDriver().quit();
    }
}
