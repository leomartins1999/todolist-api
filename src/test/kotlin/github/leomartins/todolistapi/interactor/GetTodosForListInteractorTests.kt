package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.buildTodoList
import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.domain.TodoTodoList
import github.leomartins.todolistapi.repository.TodoListRepository
import github.leomartins.todolistapi.repository.TodoRepository
import github.leomartins.todolistapi.repository.TodoTodoListRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.NoSuchElementException

@SpringBootTest
class GetTodosForListInteractorTests {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Autowired
    private lateinit var todoListRepository: TodoListRepository

    @Autowired
    private lateinit var todoTodoListRepository: TodoTodoListRepository

    @Autowired
    private lateinit var interactor: GetTodosForListInteractor

    private lateinit var todo: Todo
    private lateinit var todoList: TodoList

    @BeforeEach
    fun setup() {
        todoRepository.deleteAll()
        todoListRepository.deleteAll()
        todoTodoListRepository.deleteAll()

        todo = todoRepository.save(buildTodo())
        todoList = todoListRepository.save(buildTodoList())
    }

    @Test
    fun `gets an empty list of todos`() {
        val results = interactor.call(todoList.id)

        assert(results.isEmpty())
    }

    @Test
    fun `gets a list of todos`() {
        todoTodoListRepository.save(TodoTodoList(todoId = todo.id, todoListId = todoList.id))

        val results = interactor.call(todoList.id)

        assert(results.isNotEmpty())
        assertEquals(todo, results.first())
    }

    @Test
    fun `if the list does not exist, NoSuchElementException is thrown`() {
        assertThrows<NoSuchElementException> { interactor.call(FAKE_LIST_ID) }
    }

    private companion object {
        const val FAKE_LIST_ID = -1
    }
}
