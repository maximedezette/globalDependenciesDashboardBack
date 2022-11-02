Feature: Retrieve all dependencies

  Scenario: Retrieve all the dependencies
    When A user asks for the dependencies of all projects
    Then He should have a success response
    And All the dependencies should be displayed

