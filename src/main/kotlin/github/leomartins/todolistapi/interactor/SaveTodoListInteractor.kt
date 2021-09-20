package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.repository.TodoListRepository
import org.springframework.stereotype.Component

@Component
class SaveTodoListInteractor(
    private val repository: TodoListRepository
) {

    fun call(todoList: TodoList) = repository.save(todoList)

}