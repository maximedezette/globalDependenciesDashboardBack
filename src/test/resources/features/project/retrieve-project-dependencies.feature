Feature: Retrieve project dependencies

  Scenario: Retrieve project dependencies
    Given There is a project named 'AperoTech' with dependencies
      | groupId     | artifactId   | version |
      | io.cucumber | cucumber-bom | 7.6.0   |
    When A user asks for the dependencies of the project named 'AperoTech'
    Then The following dependencies should be displayed
      | groupId                  | artifactId                 | version |
      | io.cucumber              | cucumber-bom               | 7.6.0   |

