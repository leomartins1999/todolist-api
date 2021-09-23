package github.leomartins.todolistapi.controller

import github.leomartins.todolistapi.domain.Todo
import github.leomartins.todolistapi.dto.WriteTodo
import github.leomartins.todolistapi.interactor.GetTodosInteractor
import github.leomartins.todolistapi.interactor.SaveTodoInteractor
import github.leomartins.todolistapi.interactor.UpdateTodoInteractor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [TODOS_PATH])
class TodoController(
    private val getTodosInteractor: GetTodosInteractor,
    private val saveTodoInteractor: SaveTodoInteractor,
    private val updateTodoInteractor: UpdateTodoInteractor
) {

    @GetMapping
    fun getTodos() = getTodosInteractor.call()

    @PostMapping
    fun saveTodo(@RequestBody todo: Todo) = saveTodoInteractor.call(todo)

    @PutMapping(value = [TODO_PATH])
    fun updateTodo(@PathVariable todoId: Int, @RequestBody update: WriteTodo) =
        updateTodoInteractor.call(todoId, update)
}
