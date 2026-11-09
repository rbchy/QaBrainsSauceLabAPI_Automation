Feature: Cart Functionality
  As a shopper on the QA Brains e-commerce demo
  I want to add and remove products from my cart
  So that I can purchase the products I need

  Background:
    Given User is logged in to the e-commerce site

  @smoke @regression @Cart
  Scenario: Add product to cart
    When User adds products to cart
    Then Product should appear in cart

  @smoke @regression @Cart
  Scenario: Remove product from cart
    When User adds products to cart
    And User removes product from cart
    Then Cart should be empty
