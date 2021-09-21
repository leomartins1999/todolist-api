package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.interactor.GetTodosInteractor
import github.leomartins.todolistapi.interactor.SaveTodoInteractor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var getTodosInteractor: GetTodosInteractor

    @MockBean
    private lateinit var saveTodoInteractor: SaveTodoInteractor

    @Nested
    inner class `Get todos` {

        @Test
        fun `returns an empty array`() {
            whenever(getTodosInteractor.call()).doReturn(emptyList())

            webTestClient.getTodos()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0)
        }

        @Test
        fun `returns a list of todos`() {
            val todos = listOf(buildTodo(), buildTodo())

            whenever(getTodosInteractor.call()).doReturn(todos)

            webTestClient.getTodos()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].title").isEqualTo(todos[0].title)
                .jsonPath("$[0].description").isEqualTo(todos[0].description!!)
                .jsonPath("$[0].done").isEqualTo(todos[0].done)
                .jsonPath("$[1].title").isEqualTo(todos[1].title)
        }
    }

    @Nested
    inner class `Save todo` {

        private val todo = buildTodo()

        @BeforeEach
        fun setup() {
            whenever(saveTodoInteractor.call(any())).doReturn(todo)
        }

        @Test
        fun `saves a todo`() {
            webTestClient.saveTodo(title = todo.title, description = todo.description, done = todo.done)
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.title").isEqualTo(todo.title)
                .jsonPath("$.description").isEqualTo(todo.description!!)
                .jsonPath("$.done").isEqualTo(todo.done)
        }

        @Test
        fun `description and done are optional while creating a todo`() {
            webTestClient.saveTodo(title = todo.title)
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.title").isEqualTo(todo.title)
        }

        @Test
        fun `if a title is not passed in the body, the endpoint returns 400`() {
            webTestClient.saveTodo()
                .expectStatus()
                .is4xxClientError
        }
    }

    private fun WebTestClient.getTodos() = get()
        .uri("/todos")
        .exchange()

    private fun WebTestClient.saveTodo(
        title: String? = null,
        description: String? = null,
        done: Boolean? = null
    ) = post()
        .uri("/todos")
        .bodyValue(
            mapOf(
                "title" to title,
                "description" to description,
                "done" to done
            )
        )
        .exchange()
}
