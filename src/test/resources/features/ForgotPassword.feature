Feature: Forgot Password functionality

  @smoke @regression @ForgotPassword
  Scenario: Registered user can reset password
  Given User is on Forgot Password page
  When User enters registered email "chyranajit@gmail.com"
  And User clicks on Submit button
  Then User should see success message "Password is reset successfully"
  
  @regression @ForgotPassword
  Scenario: Unregistered user cannot reset password
    Given User is on Forgot Password page
    When User enters unregistered email "chyranajitgmail.com"
    And User clicks on Submit button
    Then User should see error message "Please include an '@' sign in the email address.'chyranajitgmail.com' is missing an'@'."