Feature: Retrieving all objectives

  Scenario: Retrieving all objectives
    Given There are objectives stored with these characteristics
      | groupId                  | artifactId   | version |
      | org.apache.logging.log4j | log4j        | 2.18.0  |
      | io.cucumber              | cucumber-bom | 7.8.0   |
    When I retrieve all objectives
    Then I should receive
      | groupId                  | artifactId   | version |
      | org.apache.logging.log4j | log4j        | 2.18.0  |
      | io.cucumber              | cucumber-bom | 7.8.0   |