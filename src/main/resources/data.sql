INSERT IGNORE INTO roles (id, title)
VALUES (1, 'Admin'),
       (2, 'Manager'),
       (3, 'Developer');

INSERT IGNORE INTO user_status (id, title)
VALUES (1, 'Active'),
       (2, 'Inactive');

INSERT IGNORE INTO project_status (id, title)
VALUES (0, 'Pre'),
       (1, 'Started'),
       (2, 'Paused'),
       (3, 'Ended');
