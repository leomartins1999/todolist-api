package github.leomartins.todolistapi.domain

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Todo(
    @Id
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val dueDate: LocalDateTime? = null,
    val difficulty: TodoDifficulty? = null,
    val status: TodoStatus? = TodoStatus.TODO
)
