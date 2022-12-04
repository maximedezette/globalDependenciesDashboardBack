Feature: Retrieve all dependencies

  Scenario: Retrieve all dependencies
    Given There is a project named 'AperoTech' stored in the database
    When A user asks for all the dependencies
    Then He should have a success response
    And All the dependencies should be displayed

