Feature: Cart Functionality
  As a user
  I want to add and remove products from cart
  So that I can purchase the products I need

  @smoke @regression
  Scenario: Add product to cart
    Given User launches the application
    When User adds products to cart
    Then Product should appear in cart

  @smoke @regression
  Scenario: Remove product from cart
    Given User launches the application
    When User adds products to cart
    And User removes product from cart
    Then Cart should be empty


