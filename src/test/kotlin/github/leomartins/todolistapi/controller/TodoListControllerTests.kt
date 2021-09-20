package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
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

}