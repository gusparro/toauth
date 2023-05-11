CREATE TABLE appuser
ALTER TABLE appuser
    ADD COLUMN code VARCHAR(36) NOT NULL default gen_random_uuid();

ALTER TABLE appuser
    ADD CONSTRAINT appuser_code_key UNIQUE (code);