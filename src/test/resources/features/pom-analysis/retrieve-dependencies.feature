Feature: Retrieve project dependencies

  Scenario: Retrieve project dependencies
    Given There is a project named 'AperoTech' stored in the database
    When A user asks for the dependencies of the project named 'AperoTech'
    Then He should have a success response
    And All the dependencies should be displayed

