INSERT INTO appuser (full_name, email, username, password)
VALUES ('Gustavo Ferreira Parro', 'gustavo.ferreira.parro@gmail.com', 'gusparro',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36');

INSERT INTO appuser (full_name, email, username, password)
VALUES ('Iann Carlo', 'iannCarlo@gmail.com', 'iann',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36');

INSERT INTO appuser (full_name, email, username, password)
VALUES ('Wendel Silva', 'wendel_silva@gmail.com', 'wendelzin',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36');

INSERT INTO role (name, description)
VALUES ('DRIVER_ROLE', 'Profile created for use by company drivers.');

INSERT INTO role (name, description)
VALUES ('MANAGER_ROLE', 'Profile created for use by company managers.');

INSERT INTO role (name, description)
VALUES ('ADMINISTRATOR_ROLE', 'Profile created for use by company administrators.');

INSERT INTO appuser_role (appuser_id, role_id)
VALUES (1, 1);

INSERT INTO appuser_role (appuser_id, role_id)
VALUES (1, 2);

INSERT INTO appuser_role (appuser_id, role_id)
VALUES (1, 3);

INSERT INTO appuser_role (appuser_id, role_id)
VALUES (2, 2);

INSERT INTO appuser_role (appuser_id, role_id)
VALUES (3, 1);
