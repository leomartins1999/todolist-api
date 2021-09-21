package github.leomartins.todolistapi.repository

import github.leomartins.todolistapi.domain.Todo
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {

    @Query(
        """
             SELECT t.*
             FROM todo as t
             RIGHT JOIN todo_todo_list as ttl
             ON t.id = ttl.todo_id
             WHERE ttl.todo_list_id = :todoListId
    """
    )
    fun findAllByTodoListId(todoListId: Int): List<Todo>
}