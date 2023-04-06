CREATE TABLE appuser_role
(
    id         SERIAL PRIMARY KEY,
    appuser_id BIGINT NOT NULL REFERENCES appuser (id),
    role_id    BIGINT NOT NULL REFERENCES role (id)
);

ALTER TABLE appuser_role
    ADD CONSTRAINT unique_user_role UNIQUE (appuser_id, role_id);

ALTER TABLE appuser_role
    OWNER TO postgres;