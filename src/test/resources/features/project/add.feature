Feature: Adding a new projects

  Scenario: Adding a new project
    When A user create a project with these information
      | name      | pomURL                                                                                                                                 |
      | AperoTech | https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/src/test/java/com/globaldashboard/fixture/pom.xml |
    Then The project should be created