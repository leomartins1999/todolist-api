CREATE TABLE IF NOT EXISTS TODO_TODO_LIST(
    todo_id INTEGER NOT NULL,
    todo_list_id INTEGER NOT NULL,
    FOREIGN KEY(todo_id) REFERENCES todo(id) ON DELETE CASCADE,
    FOREIGN KEY(todo_list_id) REFERENCES todo_list(id) ON DELETE CASCADE
);