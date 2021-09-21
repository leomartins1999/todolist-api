package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.repository.TodoListRepository
import github.leomartins.todolistapi.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.NoSuchElementException

@Component
class GetTodosForListInteractor(
    private val todoRepository: TodoRepository,
    private val todoListRepository: TodoListRepository
) {

    fun call(listId: Int): List<Todo> {
        todoListRepository.findByIdOrNull(listId)
            ?: throw NoSuchElementException()

        return todoRepository.findAllByTodoListId(listId)
    }
}
