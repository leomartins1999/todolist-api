package github.leomartins.todolistapi.dto

import github.leomartins.todolistapi.domain.Todo

data class WriteTodo(
    val title: String,
    val description: String?,
    val done: Boolean
) {
    fun toTodo() = Todo(
        title = title,
        description = description,
        done = done
    )
}
