package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.AddTodoToListInteractor
import github.leomartins.todolistapi.interactor.GetTodoListsInteractor
import github.leomartins.todolistapi.interactor.GetTodosForListInteractor
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [TODO_LISTS_PATH])
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
