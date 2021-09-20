package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodoList
import github.leomartins.todolistapi.isEqualTo
import github.leomartins.todolistapi.repository.TodoListRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetTodoListsInteractorTests {

    @Autowired
    private lateinit var repository: TodoListRepository

    @Autowired
    private lateinit var interactor: GetTodoListsInteractor

    @BeforeEach
    fun setup() {
        repository.deleteAll()
    }

    @Test
    fun `returns an empty list`() {
        val result = interactor.call()

        assert(result.isEmpty())
    }

    @Test
    fun `return list of todo lists`() {
        val lists = listOf(buildTodoList(), buildTodoList(), buildTodoList())

        repository.saveAll(lists)

        val result = interactor.call()

        assert(result.isNotEmpty())
        lists.forEach { list -> assert(result.any { result -> list.isEqualTo(result) }) }
    }

}