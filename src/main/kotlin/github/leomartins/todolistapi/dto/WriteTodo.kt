package github.leomartins.todolistapi.dto

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoDifficulty
import java.time.LocalDateTime

data class WriteTodo(
    val title: String,
    val description: String?,
    val done: Boolean,
    val dueDate: LocalDateTime? = null,
    val difficulty: TodoDifficulty? = null
) {
    fun toTodo() = Todo(
        title = title,
        description = description,
        done = done,
        dueDate = dueDate,
        difficulty = difficulty
    )
}
