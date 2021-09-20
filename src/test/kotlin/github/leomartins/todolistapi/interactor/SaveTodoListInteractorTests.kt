package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.isEqualTo
import github.leomartins.todolistapi.repository.TodoListRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SaveTodoListInteractorTests {

    @Autowired
    private lateinit var repository: TodoListRepository

    @Autowired
    private lateinit var interactor: SaveTodoListInteractor

    @BeforeEach
    fun setup() {
        repository.deleteAll()
    }

    @Test
    fun `save todo list`() {
        val todoList = TodoList(
            title = "todo-list-title",
            description = "todo-list-description"
        )

        val result = interactor.call(todoList)

        assert(todoList.isEqualTo(result))
        assert(repository.findAll().first().isEqualTo(todoList))
    }

}