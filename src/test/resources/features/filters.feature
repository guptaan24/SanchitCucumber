Feature: Filters

  @single
  Scenario: Filter By Duration
    Given I open browser
    When I navigate to Site
    And I filter on Departure Date "Apr 2019"
    Then Start Date should lie in Departure filter "April"

