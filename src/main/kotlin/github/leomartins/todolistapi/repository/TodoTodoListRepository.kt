package github.leomartins.todolistapi.repository

import github.leomartins.todolistapi.domain.TodoTodoList
import org.springframework.data.repository.CrudRepository

interface TodoTodoListRepository : CrudRepository<TodoTodoList, Int>