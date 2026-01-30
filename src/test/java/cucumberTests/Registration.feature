Feature: User Registration

@Registration
Scenario: User registration with valid details
	Given I am on the registration page on "chrome" browser
	And I fill the registration form with valid details
	And I submit the registration form
	Then the account should be created successfully
