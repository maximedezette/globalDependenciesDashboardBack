Feature: Removing a new projects

  Scenario: Removing a project
    Given There is a project named 'AperoTech' stored in the database
    When A user delete a project with name 'AperoTech'
    Then The project 'AperoTech' should be deleted