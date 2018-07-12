Feature: The home page of the weather app
  A user that navigates to the home page of the weather app wishes to use to service

  Scenario: The page renders
    Given I have navigated to the "home page"
    Then the page title should be "5 Weather Forecast"
