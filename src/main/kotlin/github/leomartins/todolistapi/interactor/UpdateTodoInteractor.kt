package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.dto.WriteTodo
import github.leomartins.todolistapi.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UpdateTodoInteractor(
    private val repository: TodoRepository
) {
    fun call(id: Int, update: WriteTodo): Todo {
        val todo = repository.findByIdOrNull(id) ?: throw NoSuchElementException()

        return repository.save(todo.update(update))
    }

    private fun Todo.update(update: WriteTodo) =
        copy(
            title = update.title,
            description = update.description,
            done = update.done
        )
}
