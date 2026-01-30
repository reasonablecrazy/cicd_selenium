package naveennarayananacademy.pageobjectmodel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import abstractComponents.Abstract_Components;

public class RegistrationPage extends Abstract_Components {
    WebDriver driver;

    @FindBy(id = "firstName")
    WebElement firstNameInput;

    @FindBy(id = "lastName")
    WebElement lastNameInput;

    @FindBy(id = "userEmail")
    WebElement emailInput;

    @FindBy(id = "userMobile")
    WebElement phoneInput;

    @FindBy(css = "select[formcontrolname='occupation']")
    WebElement occupationSelect;

    @FindBy(css = "input[formcontrolname='gender'][value='Male']")
    WebElement genderMaleRadio;

    @FindBy(css = "input[formcontrolname='gender'][value='Female']")
    WebElement genderFemaleRadio;

    @FindBy(id = "userPassword")
    WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    WebElement confirmPasswordInput;

    @FindBy(css = "input[type='checkbox'][formcontrolname='required']")
    WebElement ageCheckbox;

    @FindBy(id = "login")
    WebElement registerButton;

    @FindBy(css = "a.text-reset")
    WebElement loginHereLink;

    @FindBy(xpath = "//h1[contains(text(),'Thankyou')]")
    WebElement registrationSuccessMessage;

    @FindBy(css = "#toast-container")
    WebElement toastContainer;
    
    @FindBy(xpath = "//h1[text()='Account Created Successfully']")
    WebElement accountCreatedSuccessMessage;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RegistrationPage fillRegistrationForm(String firstName, String lastName, String email, String phone, String occupation, String gender, String password, String confirmPassword) {
        waitForElement(firstNameInput).sendKeys(firstName);
        waitForElement(lastNameInput).sendKeys(lastName);
        waitForElement(emailInput).sendKeys(email);
        waitForElement(phoneInput).sendKeys(phone);
        selectOccupation(occupation);
        selectGender(gender);
        waitForElement(passwordInput).sendKeys(password);
        waitForElement(confirmPasswordInput).sendKeys(confirmPassword);
        return this;
    }

    public RegistrationPage selectOccupation(String occupation) {
        Select select = new Select(waitForElement(occupationSelect));
        select.selectByVisibleText(occupation);
        return this;
    }

    public RegistrationPage selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            waitForElement(genderMaleRadio).click();
        } else if (gender.equalsIgnoreCase("Female")) {
            waitForElement(genderFemaleRadio).click();
        }
        return this;
    }

    public RegistrationPage checkAgeConfirmation() {
        if (!ageCheckbox.isSelected()) {
            waitForElement(ageCheckbox).click();
        }
        return this;
    }

    public void clickRegister() {
        waitForElement(registerButton).click();
    }
    
    public boolean isAccountCreatedSuccessMessageDisplayed() {
		try {
			waitForElement(accountCreatedSuccessMessage);
			return accountCreatedSuccessMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

    public void clickLoginHere() {
        waitForElement(loginHereLink).click();
    }

    public boolean isRegistrationSuccessMessageDisplayed() {
        try {
            waitForElement(registrationSuccessMessage);
            return registrationSuccessMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getToastMessage() {
        try {
            return waitForElement(toastContainer).getText();
        } catch (Exception e) {
            return null;
        }
    }
}