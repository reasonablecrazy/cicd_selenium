Feature: Test E2E order from the website

Background:
	Given I land on eCommerce page

@Regression_Cucumber
Scenario Outline: Positive test of order
	Given I logged in with the username <name> and password <password> and click on 'Submit' button
	When I add the product name <productName> to the cart
	And  I enter the country as <countryName> and submit the Checkout page
	Then order confirmation page is displayes as expected and <productName> is present on the confirmation page

			
	Examples:
	| name 					  | password | productName 	   | countryName |
	| twinklekhanna@gmail.com | Test1234 | ADIDAS ORIGINAL | Canada 	 |

