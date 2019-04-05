Feature: Filters

  @single
  Scenario: Filter By Departure
    Given I open browser
    When I navigate to Site
    And I filter on Departure Date "Apr 2019"
    Then Start Date should lie in Departure filter "April"

  @single
  Scenario: Filter By Duration
    When I call the duration Service, Duration should be greater than "24" and less than "50"