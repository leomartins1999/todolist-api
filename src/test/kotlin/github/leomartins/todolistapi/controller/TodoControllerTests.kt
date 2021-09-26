package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoDifficulty
import github.leomartins.todolistapi.interactor.GetTodosInteractor
import github.leomartins.todolistapi.interactor.SaveTodoInteractor
import github.leomartins.todolistapi.interactor.UpdateTodoInteractor
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var getTodosInteractor: GetTodosInteractor

    @MockBean
    private lateinit var saveTodoInteractor: SaveTodoInteractor

    @MockBean
    private lateinit var updateTodoInteractor: UpdateTodoInteractor

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

        @Test
        fun `saves a todo`() {
            val todo = buildTodo()

            whenever(saveTodoInteractor.call(any())).doReturn(todo)

            webTestClient.saveTodo(title = todo.title, description = todo.description, done = todo.done)
                .expectStatus()
                .isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.title").isEqualTo(todo.title)
                .jsonPath("$.description").isEqualTo(todo.description!!)
                .jsonPath("$.done").isEqualTo(todo.done)
        }

        @Test
        fun `description and done are optional while creating a todo`() {
            val todo = Todo(title = "Only title")

            whenever(saveTodoInteractor.call(any())).doReturn(todo)

            webTestClient.saveTodo(title = todo.title)
                .expectStatus()
                .isCreated
                .expectBody()
                .jsonPath("$.title").isEqualTo(todo.title)
        }

        @Test
        fun `if a title is not passed in the body, the endpoint returns 400`() {
            webTestClient.saveTodo()
                .expectStatus()
                .is4xxClientError
        }

        @Test
        fun `dueDate and difficulty are correctly serialized`() {
            val dueDateString = "2021-03-03T18:00:00"

            val todo = buildTodo().copy(
                dueDate = LocalDateTime.parse(dueDateString),
                difficulty = TodoDifficulty.MEDIUM
            )

            whenever(saveTodoInteractor.call(any())).doReturn(todo)

            webTestClient.saveTodo(
                title = todo.title,
                dueDate = dueDateString,
                difficulty = todo.difficulty.toString()
            )
                .expectStatus()
                .isCreated
                .expectBody()
                .jsonPath("$.title").isEqualTo(todo.title)
                .jsonPath("$.due_date").isEqualTo(dueDateString)
                .jsonPath("$.difficulty").isEqualTo(todo.difficulty.toString())
        }
    }

    @Nested
    inner class `Update Todo` {

        private val todoId = 1
        private val updatedTitle = "New title"

        @Test
        fun `updates a todo`() {
            whenever(updateTodoInteractor.call(any(), any())).doReturn(
                Todo(
                    id = todoId,
                    title = updatedTitle
                )
            )

            webTestClient.updateTodo(todoId, title = updatedTitle)
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.title").isEqualTo(updatedTitle)
        }

        @Test
        fun `if the todo does not exist, the endpoint responds with 404`() {
            whenever(updateTodoInteractor.call(any(), any())).doThrow(NoSuchElementException())

            webTestClient.updateTodo(todoId, title = updatedTitle)
                .expectStatus()
                .isNotFound
        }
    }

    private fun WebTestClient.getTodos() = get()
        .uri("/todos")
        .exchange()

    private fun WebTestClient.saveTodo(
        title: String? = null,
        description: String? = null,
        done: Boolean? = null,
        dueDate: String? = null,
        difficulty: String? = null
    ) = post()
        .uri("/todos")
        .bodyValue(buildWriteTodo(title, description, done, dueDate, difficulty))
        .exchange()

    private fun WebTestClient.updateTodo(
        id: Int,
        title: String = "title",
        description: String? = null,
        done: Boolean? = null
    ) = put()
        .uri("/todos/$id")
        .bodyValue(buildWriteTodo(title, description, done))
        .exchange()

    private fun buildWriteTodo(
        title: String? = null,
        description: String? = null,
        done: Boolean? = null,
        dueDate: String? = null,
        difficulty: String? = null
    ) = mapOf(
        "title" to title,
        "description" to description,
        "done" to done,
        "due_date" to dueDate,
        "difficulty" to difficulty
    )
}
