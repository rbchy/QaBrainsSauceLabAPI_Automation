Feature: Smoke Tests
@smoke
Scenario: S-01 Verify Home page loads
  Given User launches the application
  Then Home page should load successfully
@smoke
Scenario: S-02 Verify navigation links
  Given User launches the application
  When User clicks on Catalog, About and Blog
  Then Pages should navigate correctly
@smoke
Scenario: S-03 Verify wish list and refer a friend
  Given User launches the application
  When User clicks on Wish list and Refer a Friend
  Then Pages should open successfully