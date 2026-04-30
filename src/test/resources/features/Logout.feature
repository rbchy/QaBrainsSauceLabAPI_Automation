Feature: Logout Functionality

  @smoke @regression @Logout
  Scenario: Logged in user should be able to logout
    Given User launches the application
    And User is logged in
    When User clicks on logout button
    Then User should be logged out successfully