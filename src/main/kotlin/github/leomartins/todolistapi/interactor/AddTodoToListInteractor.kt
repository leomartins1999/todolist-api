package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.TodoTodoList
import github.leomartins.todolistapi.repository.TodoListRepository
import github.leomartins.todolistapi.repository.TodoRepository
import github.leomartins.todolistapi.repository.TodoTodoListRepository
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

@Component
class AddTodoToListInteractor(
    private val todoListRepository: TodoListRepository,
    private val todoRepository: TodoRepository,
    private val associationRepository: TodoTodoListRepository
) {

    fun call(todoListId: Int, todoId: Int) {
        todoListRepository.findByIdOrNull(todoListId) ?: throw NoSuchElementException()
        todoRepository.findByIdOrNull(todoId) ?: throw NoSuchElementException()

        try {
            associationRepository.save(TodoTodoList(todoListId = todoListId, todoId = todoId))
        } catch (ex: DbActionExecutionException) {
            throw IllegalStateException()
        }
    }

}