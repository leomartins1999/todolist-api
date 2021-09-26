package github.leomartins.todolistapi.dto

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoDifficulty
import github.leomartins.todolistapi.domain.TodoStatus
import java.time.LocalDateTime

data class WriteTodo(
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime? = null,
    val difficulty: TodoDifficulty? = null,
    val status: TodoStatus? = TodoStatus.TODO
) {
    fun toTodo() = Todo(
        title = title,
        description = description,
        dueDate = dueDate,
        difficulty = difficulty,
        status = status
    )
}
