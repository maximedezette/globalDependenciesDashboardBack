Feature: Retrieve all projects

  Scenario: Retrieve all projects
    Given There are projects stored in the database
    When A user asks for all projects
    Then He should have a success response
    And All the projects should be displayed