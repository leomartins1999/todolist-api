package github.leomartins.todolistapi.interactor

import github.leomartins.todolistapi.buildTodo
import github.leomartins.todolistapi.buildTodoList
import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.domain.TodoTodoList
import github.leomartins.todolistapi.repository.TodoListRepository
import github.leomartins.todolistapi.repository.TodoRepository
import github.leomartins.todolistapi.repository.TodoTodoListRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalStateException

@SpringBootTest
class AddTodoToListInteractorTests {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Autowired
    private lateinit var todoListRepository: TodoListRepository

    @Autowired
    private lateinit var todoTodoListRepository: TodoTodoListRepository

    @Autowired
    private lateinit var interactorTo: AddTodoToListInteractor

    private lateinit var todo: Todo
    private lateinit var todoList: TodoList

    @BeforeEach
    fun setup() {
        todoRepository.deleteAll()
        todoListRepository.deleteAll()
        todoTodoListRepository.deleteAll()

        todo = todoRepository.save(buildTodo())
        todoList = todoListRepository.save(buildTodoList())
    }

    @Test
    fun `add todo to todo list`() {
        interactorTo.call(todoList.id, todo.id)

        val association = todoTodoListRepository.findAll().first()
        assertEquals(todoList.id, association.todoListId)
        assertEquals(todo.id, association.todoId)
    }

    @Test
    fun `a list can have multiple todos`() {
        todoTodoListRepository.save(TodoTodoList(todoListId = todoList.id, todoId = todo.id))

        val newTodo = todoRepository.save(buildTodo())

        interactorTo.call(todoList.id, newTodo.id)

        val associations = todoTodoListRepository.findAll().toList()
        assertEquals(2, associations.size)
        assert(associations.all { it.todoListId == todoList.id })
        assertEquals(todo.id, associations[0].todoId)
        assertEquals(newTodo.id, associations[1].todoId)
    }

    @Test
    fun `adding a duplicate association will throw an IllegalStateException`() {
        todoTodoListRepository.save(TodoTodoList(todoListId = todoList.id, todoId = todo.id))

        assertThrows<IllegalStateException> { interactorTo.call(todoList.id, todo.id) }
    }

    @Test
    fun `referencing an non existent list will throw a NoSuchElementException`() {
        assertThrows<NoSuchElementException> { interactorTo.call(FAKE_TODO_LIST_ID, todo.id) }
    }

    @Test
    fun `referencing an non existent todo will throw a NoSuchElementException`() {
        assertThrows<NoSuchElementException> { interactorTo.call(todoList.id, FAKE_TODO_ID) }
    }

    private companion object {
        const val FAKE_TODO_ID = -1
        const val FAKE_TODO_LIST_ID = -1
    }
}
