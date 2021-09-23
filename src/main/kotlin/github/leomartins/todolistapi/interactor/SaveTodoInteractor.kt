package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.dto.WriteTodo
import github.leomartins.todolistapi.repository.TodoRepository
import org.springframework.stereotype.Component

@Component
class SaveTodoInteractor(
    private val repository: TodoRepository
) {

    fun call(todo: WriteTodo) = repository.save(todo.toTodo())
}
