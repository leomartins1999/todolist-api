package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.interactor.GetTodosInteractor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [TODO_PATH])
class TodoController(
    private val getTodosInteractor: GetTodosInteractor
) {

    @GetMapping
    fun getTodos() = getTodosInteractor.call()
}