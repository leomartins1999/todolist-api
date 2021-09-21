package github.leomartins.todolistapi.domain

import org.springframework.data.annotation.Id

data class TodoTodoList(
    @Id
    val id: Int = 0,
    val todoId: Int,
    val todoListId: Int
)
