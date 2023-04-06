CREATE TABLE role
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

ALTER TABLE role
    OWNER TO postgres;