Feature: Getting project status

  Scenario: Getting project status
    Given There is a project named 'AperoTech' with dependencies
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.0   |
      | io.cucumber              | cucumber-bom               | 7.6.0   |
    And There are objectives stored with these characteristics
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.7.0   |
      | io.cucumber              | cucumber-bom               | 7.6.0   |
    When I ask the status of the first project
    Then I should receive
      | groupId                  | artifactId                 | version | isAchieved |
      | io.cucumber              | cucumber-bom               | 7.6.0   | true       |
      | org.springframework.boot | spring-boot-starter-parent | 2.7.0   | false      |
