package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.repository.TodoRepository
import org.springframework.stereotype.Component

@Component
class GetTodosInteractor(
    private val todoRepository: TodoRepository
) {

    fun call(): List<Todo> = todoRepository.findAll().toList()

}