Feature: Adding a new projects

  Scenario: Adding a new project
    When A user create a project with these information
      | name      | pomURL          |
      | AperoTech | https://pom.xml |
    Then The project should be created