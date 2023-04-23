Feature: Getting project status

  Scenario: Getting project status
    Given There are projects stored in the database
    And There are objectives stored in the database with these characteristics
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.7.0   |
      | io.cucumber              | cucumber-bom               | 7.6.0   |
    When I ask the status of the first project
    Then I should get the first project status
