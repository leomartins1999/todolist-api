package github.leomartins.todolistapi.domain

import org.springframework.data.annotation.Id

data class Todo(
    @Id
    val id: Int = 0,
    val title: String,
    val description: String?,
    val done: Boolean
)
