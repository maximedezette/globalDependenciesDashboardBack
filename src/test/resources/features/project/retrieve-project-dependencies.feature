Feature: Retrieve project dependencies

  Scenario: Retrieve project dependencies
    Given There is a project named 'AperoTech' with dependencies
      | groupId     | artifactId   | version |
      | io.cucumber | cucumber-bom | 7.6.0   |
    When A user asks for the dependencies of the project named 'AperoTech'
    Then The following dependencies should be displayed
      | groupId     | artifactId   | version |
      | io.cucumber | cucumber-bom | 7.6.0   |


  Scenario: Retrieve project dependencies with security status
    Given There is a project named 'AperoTech' with dependencies
      | groupId     | artifactId   | version |
      | io.cucumber | cucumber-bom | 7.6.0   |
    And The CVE 'CVE-2023-35116' affect the dependencies
    When A user asks for the dependencies of the project named 'AperoTech' with security issues
    Then The following dependencies should be displayed
      | groupId     | artifactId   | version | secured | CVE            |
      | io.cucumber | cucumber-bom | 7.6.0   | false   | CVE-2023-35116 |