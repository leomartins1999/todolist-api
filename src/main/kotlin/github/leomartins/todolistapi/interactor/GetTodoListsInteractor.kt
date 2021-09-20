package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.repository.TodoListRepository
import org.springframework.stereotype.Component

@Component
class GetTodoListsInteractor(
    private val repository: TodoListRepository
) {

    fun call(): List<TodoList> = repository.findAll().toList()

}