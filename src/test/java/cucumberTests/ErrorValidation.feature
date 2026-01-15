Feature: Error Validations

Background:
	Given I land on eCommerce page

@ErrorValidation
Scenario Outline: Incorrect Loginr
	Given I logged in with the username <name> and password <password> and click on 'Submit' button
	But incorrect login error message is displayed

			
	Examples:
	| name 					  | password |
	| twinklekhanna@gmail.com | Test12345 |

