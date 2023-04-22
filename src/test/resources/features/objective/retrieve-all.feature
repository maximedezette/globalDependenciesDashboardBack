Feature: Retrieving all objectives

  Scenario: Retrieving all objectives
    Given There are objectives stored in the database
    When I retrieve all objectives
    Then I should receive all objectives