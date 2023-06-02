Feature: Deleting an objective

  Scenario: Deleting a registered objective
    Given The following objectives are already set
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.1   |
      | io.cucumber              | cucumber-bom               | 7.0.0   |
    And I delete the objective with these information
      | groupId                  | artifactId                 |
      | org.springframework.boot | spring-boot-starter-parent |
    When I retrieve all objectives
    Then I should retrieve the following objectives
      | groupId                  | artifactId                 | version |
      | io.cucumber              | cucumber-bom               | 7.0.0   |
