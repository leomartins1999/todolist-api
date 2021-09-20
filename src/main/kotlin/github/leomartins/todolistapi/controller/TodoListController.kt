package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.TodoList
import github.leomartins.todolistapi.interactor.SaveTodoListInteractor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [TODO_LIST_PATH])
class TodoListController(
    private val saveTodoListInteractor: SaveTodoListInteractor
) {

    @PostMapping
    fun saveTodoList(
        @RequestBody todoList: TodoList
    ) = saveTodoListInteractor.call(todoList)

}