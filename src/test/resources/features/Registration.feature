Feature: User Registration

Background:
  Given User launches the application
  And User is on homepage

@smoke @regression @Registration
Scenario: User should be able to register successfully with a valid email
  When User navigates to registration page
  And User enters valid registration details with email "chyranajit@gmail.com"
  And User clicks on register button
  Then User should be registered successfully

@regression @Registration
Scenario: User should not be able to register with invalid email
  When User navigates to registration page
  And User enters registration details with email "chyranajitgmail.com"
  And User clicks on register button
  Then Email validation error message should be displayed