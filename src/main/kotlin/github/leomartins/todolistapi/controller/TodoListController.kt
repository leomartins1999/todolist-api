package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.GetTodoListsInteractor
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = [TODO_LIST_PATH])
class TodoListController(
    private val saveTodoListInteractor: SaveTodoListInteractor,
    private val getTodoListsInteractor: GetTodoListsInteractor
) {

    @GetMapping
    fun getTodoLists() = getTodoListsInteractor.call()

    @PostMapping
    fun saveTodoList(
        @RequestBody todoList: TodoList
    ) = saveTodoListInteractor.call(todoList)

}