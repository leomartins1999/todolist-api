package github.leomartins.todolistapi

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoList

fun buildTodo() = Todo(
    title = "todo-${System.currentTimeMillis()}",
    description = "desc",
    done = false
)

fun Todo.isEqualTo(other: Todo) = title == other.title && description == other.description && done == other.done

fun buildTodoList() = TodoList(
    title = "todo-list-${System.currentTimeMillis()}",
    description = "todo-list-description"
)

fun TodoList.isEqualTo(other: TodoList) = title == other.title && description == other.description