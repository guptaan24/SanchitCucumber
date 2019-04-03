Feature: Filters

  @single
  Scenario: Filter By Duration
    Given I open browser
    When I navigate to Site
    And I filter on Departure Date "Apr 2019"
    Then Start Date should lie in Departure filter "April"

#  @single
#  Scenario: Filter By Departure date
#    Given I open browser
#    When I navigate to Site
#    And I provide username as hi and password as hi
#    And I click on login button
#    Then hi should be name
#
#  @multiple
#  Scenario Outline: Successful Login to the page
#    Given I open firefox browser
#    When I navigate to login.html page
#    And I provide username as "<username>" and password as "<password>"
#    And I click on login button
#    Then name should be "<name>"
#
#    Examples:
#      | username | password | name |
#      | username1 | password1 | username1 |
#      | username2 | password2 | username1 |