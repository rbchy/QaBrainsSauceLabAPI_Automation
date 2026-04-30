Feature: Login Functionality
  As a user
  I want to login to the application
  So that I can access my account

  @smoke @regression @Login
  Scenario: Login with valid credentials
    Given User launches the application
    When User enters valid username and password
    Then User should be logged in successfully

  @regression @Login
  Scenario: Login with invalid credentials
    Given User launches the application
    When User enters invalid username and password
    Then Error message should be displayed