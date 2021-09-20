CREATE TABLE IF NOT EXISTS todo_list(
    id SERIAL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(256)
);
