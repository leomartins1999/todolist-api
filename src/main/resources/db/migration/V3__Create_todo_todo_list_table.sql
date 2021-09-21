CREATE TABLE IF NOT EXISTS TODO_TODO_LIST(
    id SERIAL,
    todo_id INTEGER NOT NULL,
    todo_list_id INTEGER NOT NULL,
    UNIQUE(todo_id, todo_list_id),
    FOREIGN KEY(todo_id) REFERENCES todo(id) ON DELETE CASCADE,
    FOREIGN KEY(todo_list_id) REFERENCES todo_list(id) ON DELETE CASCADE
);