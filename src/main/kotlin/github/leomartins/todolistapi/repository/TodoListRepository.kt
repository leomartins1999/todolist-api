package github.leomartins.todolistapi.repository

import github.leomartins.todolistapi.domain.TodoList
import org.springframework.data.repository.CrudRepository

interface TodoListRepository : CrudRepository<TodoList, Int>
