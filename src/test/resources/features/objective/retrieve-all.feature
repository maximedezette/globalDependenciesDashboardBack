Feature: Retrieving all objectives

  Scenario: Retrieving all objectives
    Given There are objectives stored with these characteristics
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.1   |
      | io.cucumber              | cucumber-bom               | 7.6.0   |
    When I retrieve all objectives
    Then I should receive
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.1   |
      | io.cucumber              | cucumber-bom               | 7.6.0   |