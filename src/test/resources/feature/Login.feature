@test
Feature: Login test

  Scenario Outline: Login with invalid username
    When User enter username as "<username>"
    And User enter password as "<password>"
    And clicks on login
    Then login should fail with an error "<error_message>"
    Examples:
      | username | password | error_message |
      | invalidUsername | secret_sauce | Username and password do not match any user in this service. |

  Scenario Outline: Login with invalid password
    When User enter username as "<username>"
    And User enter password as "<password>"
    And clicks on login
    Then login should fail with an error "<error_message>"
    Examples:
      | username | password | error_message |
      | standard_user | invalidPassword | Username and password do not match any user in this service. |

  Scenario Outline: Login with valid username and password
    When User enter username as "<username>"
    And User enter password as "<password>"
    And clicks on login
    Then User should see Products page with title "<title>"
    Examples:
      | username | password | title |
      | standard_user | secret_sauce | PRODUCTS |