CREATE TABLE IF NOT EXISTS todo_list(
    id SERIAL PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(256)
);
