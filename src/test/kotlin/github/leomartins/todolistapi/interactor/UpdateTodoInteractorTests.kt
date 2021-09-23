package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.dto.WriteTodo
import github.leomartins.todolistapi.repository.TodoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class UpdateTodoInteractorTests {

    @Autowired
    private lateinit var repository: TodoRepository

    @Autowired
    private lateinit var interactor: UpdateTodoInteractor

    private lateinit var todo: Todo

    @BeforeEach
    fun setup() {
        repository.deleteAll()

        todo = repository.save(buildTodo())
    }

    @Test
    fun `updates a todo`() {
        val update = buildUpdateTodo()

        val result = interactor.call(todo.id, update)

        val updatedTodo = repository.findByIdOrNull(todo.id)!!

        assertEquals(update.title, updatedTodo.title)
        assertEquals(update.description, updatedTodo.description)
        assertEquals(update.done, updatedTodo.done)

        assertEquals(todo.id, result.id)
        assertEquals(update.title, result.title)
        assertEquals(update.description, result.description)
        assertEquals(update.done, result.done)
    }

    @Test
    fun `throws NoSuchElementException if the todo does not exist`() {
        assertThrows<NoSuchElementException> {
            interactor.call(FAKE_TODO_ID, buildUpdateTodo())
        }
    }

    private fun buildUpdateTodo() = WriteTodo(
        "Updated title",
        "Updated description",
        true
    )

    private companion object {
        const val FAKE_TODO_ID = -1
    }
}
