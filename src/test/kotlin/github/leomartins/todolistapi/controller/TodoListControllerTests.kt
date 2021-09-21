package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.buildTodoList
import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.AddTodoToListInteractor
import github.leomartins.todolistapi.interactor.GetTodoListsInteractor
import github.leomartins.todolistapi.interactor.GetTodosForListInteractor
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import java.lang.IllegalStateException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoListControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var saveTodoListInteractor: SaveTodoListInteractor

    @MockBean
    private lateinit var getTodoListInteractor: GetTodoListsInteractor

    @MockBean
    private lateinit var getTodosForListInteractor: GetTodosForListInteractor

    @MockBean
    private lateinit var addTodoToListInteractor: AddTodoToListInteractor

    @Nested
    inner class `Save todo list` {

        private val todoList = TodoList(
            title = "todo-list-title",
            description = "todo-list-description"
        )

        @BeforeEach
        fun setup() {
            whenever(saveTodoListInteractor.call(any())).thenReturn(todoList)
        }

        @Test
        fun `save a todo list`() {
            webTestClient.saveTodoList(title = todoList.title)
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .consumeWith { println(it.responseBody.contentToString()) }
                .jsonPath("$.title").isEqualTo(todoList.title)
        }

        @Test
        fun `todo-list title is mandatory`() {
            webTestClient.saveTodoList()
                .expectStatus()
                .isBadRequest
        }

    }

    @Nested
    inner class `Get Todo Lists` {
        @Test
        fun `returns no results`() {
            whenever(getTodoListInteractor.call()).doReturn(emptyList())

            webTestClient.getTodoLists()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0)
        }

        @Test
        fun `returns lists`() {
            val lists = listOf(buildTodoList(), buildTodoList(), buildTodoList())

            whenever(getTodoListInteractor.call()).doReturn(lists)

            webTestClient.getTodoLists()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(lists.size)
                .jsonPath("$[0].title").isEqualTo(lists.first().title)
        }
    }

    @Nested
    inner class `Get Todos for List` {
        @Test
        fun `returns no todos`() {
            whenever(getTodosForListInteractor.call(any())).doReturn(emptyList())

            webTestClient.getTodosForList()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0)
        }

        @Test
        fun `returns a list of todos`() {
            val todos = listOf(buildTodo(), buildTodo(), buildTodo())

            whenever(getTodosForListInteractor.call(any())).doReturn(todos)

            webTestClient.getTodosForList()
                .expectStatus()
                .is2xxSuccessful
                .expectBody()
                .jsonPath("$.length()").isEqualTo(todos.size)
                .jsonPath("$[0].title").isEqualTo(todos[0].title)
                .jsonPath("$[1].title").isEqualTo(todos[1].title)
        }

        @Test
        fun `if the list does not exist, response is 404`() {
            whenever(getTodosForListInteractor.call(any())).doThrow(NoSuchElementException())

            webTestClient.getTodosForList()
                .expectStatus()
                .isNotFound
        }
    }

    @Nested
    inner class `Add todo to List` {
        @Test
        fun `if the operation was successful, respond with no content`() {
            whenever(addTodoToListInteractor.call(any(), any())).doAnswer {}

            webTestClient.addTodoToList()
                .expectStatus()
                .isNoContent
        }

        @Test
        fun `if either the list or the todo do not exist, respond with not found`() {
            whenever(addTodoToListInteractor.call(any(), any())).doThrow(NoSuchElementException())

            webTestClient.addTodoToList()
                .expectStatus()
                .isNotFound
        }

        @Test
        fun `if the operation is not supported, respond with forbidden`() {
            whenever(addTodoToListInteractor.call(any(), any())).doThrow(IllegalStateException())

            webTestClient.addTodoToList()
                .expectStatus()
                .isForbidden
        }
    }

    private fun WebTestClient.saveTodoList(
        title: String? = null,
        description: String? = null
    ) = post()
        .uri("/todo-lists")
        .bodyValue(
            mapOf(
                "title" to title,
                "description" to description
            )
        )
        .exchange()

    private fun WebTestClient.getTodoLists() = get()
        .uri("/todo-lists")
        .exchange()

    private fun WebTestClient.getTodosForList(todoListId: Int = 1) = get()
        .uri("/todo-lists/$todoListId/todos")
        .exchange()

    private fun WebTestClient.addTodoToList(todoListId: Int = 1, todoId: Int = 1) = put()
        .uri("/todo-lists/$todoListId/todos/$todoId")
        .exchange()

}