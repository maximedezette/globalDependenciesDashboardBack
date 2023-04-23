Feature: Adding a new objective

  Scenario: Adding a new objective
    When A user create an objective with these information
      | groupId                  | artifactId                 | version |
      | org.springframework.boot | spring-boot-starter-parent | 2.6.1   |
    Then The objective should be created