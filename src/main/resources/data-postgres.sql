DELETE
FROM project_dependency;
DELETE
FROM objective;
DELETE
FROM dependency;
DELETE
FROM project;

INSERT INTO objective (id, artifact_id, group_id, version)
VALUES (1, 'cucumber-bom', 'io.cucumber', '7.8.0');

INSERT INTO objective (id, artifact_id, group_id, version)
VALUES (2, 'log4j', 'org.apache.logging.log4j', '2.18.0');

INSERT INTO project (id, description, java_version, name, pom_url)
VALUES (1, '', 17, 'AperoTech',
        'https://raw.githubusercontent.com/maximedezette/kata-api/main/pom.xml');

INSERT INTO project (id, description, java_version, name, pom_url)
VALUES (2, '', 21, 'LyonCraft',
        'https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/src/test/java/com/globaldashboard/fixture/pom.xml');

INSERT INTO dependency(id, artifact_id, group_id, version)
VALUES (1, 'log4j', 'org.apache.logging.log4j', '2.20.0');

INSERT INTO dependency(id, artifact_id, group_id, version)
VALUES (2, 'log4j', 'org.apache.logging.log4j', '2.18.0');

INSERT INTO dependency(id, artifact_id, group_id, version)
VALUES (3, 'cucumber-bom', 'io.cucumber', '7.6.0');

INSERT INTO dependency(id, artifact_id, group_id, version)
VALUES (4, 'cucumber-bom', 'io.cucumber', '7.8.0');

INSERT INTO project_dependency(project_id, dependency_id)
VALUES (2, 1);

INSERT INTO project_dependency(project_id, dependency_id)
VALUES (2, 4);

INSERT INTO project_dependency(project_id, dependency_id)
VALUES (1, 2);

INSERT INTO project_dependency(project_id, dependency_id)
VALUES (1, 3);
