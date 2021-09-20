package github.leomartins.todolistapi.domain

import org.springframework.data.annotation.Id

data class TodoList(
    @Id
    val id: Int = 0,
    val title: String,
    val description: String?
)
