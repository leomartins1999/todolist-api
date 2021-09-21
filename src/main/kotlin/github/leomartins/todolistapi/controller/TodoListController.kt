package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.AddTodoToListInteractor
import github.leomartins.todolistapi.interactor.GetTodoListsInteractor
import github.leomartins.todolistapi.interactor.GetTodosForListInteractor
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse

@RestController
@RequestMapping(value = [TODO_LIST_PATH])
class TodoListController(
    private val saveTodoListInteractor: SaveTodoListInteractor,
    private val getTodoListsInteractor: GetTodoListsInteractor,
    private val getTodosForListInteractor: GetTodosForListInteractor,
    private val addTodoToListInteractor: AddTodoToListInteractor
) {

    @GetMapping
    fun getTodoLists() = getTodoListsInteractor.call()

    @PostMapping
    fun saveTodoList(@RequestBody todoList: TodoList) = saveTodoListInteractor.call(todoList)

    @GetMapping(value = [TODOS_FOR_LIST_PATH])
    fun getTodosForList(@PathVariable listId: Int) = getTodosForListInteractor.call(listId)

    @PutMapping(value = [ADD_TODO_TO_LIST_PATH])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun addTodoToList(@PathVariable listId: Int, @PathVariable todoId: Int) =
        addTodoToListInteractor.call(listId, todoId)

}