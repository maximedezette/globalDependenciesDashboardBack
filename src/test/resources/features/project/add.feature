Feature: Adding a new projects

  Scenario: Adding a new project
    Given There are not projects stored in the database
    When A user create a project with these information
      | name      | pomURL                                                                             |
      | AperoTech | https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml |
    Then The project should be created