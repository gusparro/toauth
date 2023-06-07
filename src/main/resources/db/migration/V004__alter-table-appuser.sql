ALTER TABLE appuser
    ADD COLUMN codeuuid VARCHAR(36) NOT NULL default gen_random_uuid();

ALTER TABLE appuser
    ADD CONSTRAINT appuser_codeuuid_key UNIQUE (codeuuid);