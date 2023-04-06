DELETE FROM appuser_role WHERE TRUE;
DELETE FROM appuser WHERE TRUE;
DELETE FROM role WHERE TRUE;

ALTER SEQUENCE appuser_role_id_seq RESTART WITH 1;
ALTER SEQUENCE appuser_id_seq RESTART WITH 1;
ALTER SEQUENCE role_id_seq RESTART WITH 1;

INSERT INTO appuser (id, full_name, email, username, password)
VALUES (1, 'Gustavo Ferreira Parro', 'gustavo.ferreira.parro@gmail.com', 'gusparro',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36')
ON CONFLICT DO NOTHING;

INSERT INTO appuser (id, full_name, email, username, password)
VALUES (2, 'Iann Carlo', 'iannCarlo@gmail.com', 'iann',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36')
ON CONFLICT DO NOTHING;

INSERT INTO appuser (id, full_name, email, username, password)
VALUES (3, 'Wendel Silva', 'wendel_silva@gmail.com', 'wendelzin',
        '$2a$10$I0Ti.ZrqTA02wuCzStTmK.ywtZSZ1a76FGGUqlQzJA8lwMZoQgN36')
ON CONFLICT DO NOTHING;

INSERT INTO role (id, name, description)
VALUES (1, 'DRIVER_ROLE', 'Profile created for use by company drivers.')
ON CONFLICT DO NOTHING;

INSERT INTO role (id, name, description)
VALUES (2, 'MANAGER_ROLE', 'Profile created for use by company managers.')
ON CONFLICT DO NOTHING;

INSERT INTO role (id, name, description)
VALUES (3, 'ADMINISTRATOR_ROLE', 'Profile created for use by company administrators.')
ON CONFLICT DO NOTHING;

INSERT INTO appuser_role (id, appuser_id, role_id)
VALUES (1, 1, 1)
ON CONFLICT DO NOTHING;

INSERT INTO appuser_role (id, appuser_id, role_id)
VALUES (2, 1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO appuser_role (id, appuser_id, role_id)
VALUES (3, 1, 3)
ON CONFLICT DO NOTHING;

INSERT INTO appuser_role (id, appuser_id, role_id)
VALUES (4, 2, 2)
ON CONFLICT DO NOTHING;

INSERT INTO appuser_role (id, appuser_id, role_id)
VALUES (5, 3, 1)
ON CONFLICT DO NOTHING;
