Feature: Retrieve all dependencies

  Scenario: Retrieve all dependencies
    Given There is a project named 'AperoTech' with dependencies
      | groupId     | artifactId   | version |
      | io.cucumber | cucumber-bom | 7.6.0   |
    And There is a project named 'Kata Api' with dependencies
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.0   |
    When A user asks for all the dependencies
    Then The following dependencies should be displayed
      | groupId                  | artifactId                 | version |
      | io.cucumber              | cucumber-bom               | 7.6.0   |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.0   |

