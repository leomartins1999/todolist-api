CREATE TABLE IF NOT EXISTS todo(
    id SERIAL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(256),
    done BOOLEAN NOT NULL
);
