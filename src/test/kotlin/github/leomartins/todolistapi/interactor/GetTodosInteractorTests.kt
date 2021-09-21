package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.repository.TodoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetTodosInteractorTests {

    @Autowired
    private lateinit var repository: TodoRepository

    @Autowired
    private lateinit var getTodosInteractor: GetTodosInteractor

    @BeforeEach
    fun setup() = repository.deleteAll()

    @Test
    fun `returns no results`() {
        val res = getTodosInteractor.call()

        assert(res.isEmpty())
    }

    @Test
    fun `returns list of 1 todo`() {
        val todo = repository.save(buildTodo())

        val results = getTodosInteractor.call()

        assert(results.isNotEmpty())
        assertEquals(todo, results.first())
    }

    @Test
    fun `returns list of multiple todos`() {
        val todos = repository.saveAll(listOf(buildTodo(), buildTodo(), buildTodo()))

        val results = getTodosInteractor.call()

        assert(results.isNotEmpty())
        todos.forEach { expected ->
            assert(results.any { todo -> todo == expected })
        }
    }
}
