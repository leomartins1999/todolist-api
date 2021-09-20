package github.leomartins.todolistapi.repository

import github.leomartins.todolistapi.domain.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int>