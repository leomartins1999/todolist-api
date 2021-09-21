package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.interactor.GetTodosInteractor
import github.leomartins.todolistapi.interactor.SaveTodoInteractor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [TODO_PATH])
class TodoController(
    private val getTodosInteractor: GetTodosInteractor,
    private val saveTodoInteractor: SaveTodoInteractor
) {

    @GetMapping
    fun getTodos() = getTodosInteractor.call()

    @PostMapping
    fun saveTodo(@RequestBody todo: Todo) = saveTodoInteractor.call(todo)
}
