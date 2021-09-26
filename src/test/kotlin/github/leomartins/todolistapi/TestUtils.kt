package github.leomartins.todolistapi

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.domain.TodoStatus
import github.leomartins.todolistapi.dto.WriteTodo

fun buildTodo() = Todo(
    title = "todo-${System.currentTimeMillis()}",
    description = "desc",
    status = TodoStatus.TODO
)

fun Todo.isEqualTo(other: Todo) = title == other.title && description == other.description && status == other.status

fun Todo.toWriteTodo() = WriteTodo(title, description, status = status)

fun buildTodoList() = TodoList(
    title = "todo-list-${System.currentTimeMillis()}",
    description = "todo-list-description"
)

fun TodoList.isEqualTo(other: TodoList) = title == other.title && description == other.description
