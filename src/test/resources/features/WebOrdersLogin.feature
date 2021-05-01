@HR-7
Feature: Validating Login functionality

  @smoke @regression
  Scenario: Validating login functionality with valid credentials
    Given User navigates to application
    When User log in with username "Tester" and password "test"
    Then User validates that application is on homepage

    @smoke @regression
    Scenario: Validating login functionality with invalid credentials
      Given User navigates to application
      When User log in with username "Tester" and password "tester"
      Then User validates error login message "Invalid Login or Password."
