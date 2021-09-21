package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.repository.TodoRepository
import org.springframework.stereotype.Component

@Component
class SaveTodoInteractor(
    private val repository: TodoRepository
) {

    fun call(todo: Todo) = repository.save(todo)
}
