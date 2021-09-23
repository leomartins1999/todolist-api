package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.isEqualTo
import github.leomartins.todolistapi.repository.TodoRepository
import github.leomartins.todolistapi.toWriteTodo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SaveTodoInteractorTests {

    @Autowired
    private lateinit var repository: TodoRepository

    @Autowired
    private lateinit var interactor: SaveTodoInteractor

    @BeforeEach
    fun setup() = repository.deleteAll()

    @Test
    fun `saves a todo`() {
        val todo = buildTodo()

        val result = interactor.call(todo.toWriteTodo())

        assert(todo.isEqualTo(result))
        assertEquals(1, repository.count())
        assert(todo.isEqualTo(repository.findAll().first()))
    }

    @Test
    fun `saves another todo`() {
        val todo = repository.save(buildTodo())

        val anotherTodo = todo.copy(title = "Updated todo")

        interactor.call(anotherTodo.toWriteTodo())

        assertEquals(2, repository.count())
    }
}
