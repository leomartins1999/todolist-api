package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.buildTodoList
import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.GetTodoListsInteractor
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
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
class TodoListControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var saveTodoListInteractor: SaveTodoListInteractor

    @MockBean
    private lateinit var getTodoListInteractor: GetTodoListsInteractor

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
    inner class `Get Todo List` {
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

}