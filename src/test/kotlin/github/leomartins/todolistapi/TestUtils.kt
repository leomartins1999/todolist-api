package github.leomartins.todolistapi

import github.leomartins.todolistapi.domain.Todo

fun buildTodo() = Todo(
    title = "todo-${System.currentTimeMillis()}",
    description = "desc",
    done = false
)

fun Todo.isEqualTo(other: Todo) = title == other.title && description == other.description && done == other.done