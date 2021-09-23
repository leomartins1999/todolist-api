package github.leomartins.todolistapi.dto

data class WriteTodo(
    val title: String,
    val description: String?,
    val done: Boolean
)
