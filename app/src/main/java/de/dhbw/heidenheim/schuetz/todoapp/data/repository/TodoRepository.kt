package de.dhbw.heidenheim.schuetz.todoapp.data.repository

import de.dhbw.heidenheim.schuetz.todoapp.data.local.TodoDao
import de.dhbw.heidenheim.schuetz.todoapp.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos()
    }

    suspend fun toggleTodo(id: Int) {
        val todo = todoDao.getTodoById(id) ?: return
        todoDao.updateTodo(todo.copy(isDone = !todo.isDone))
    }

    suspend fun deleteTodo(id: Int) {
        todoDao.deleteTodoById(id)
    }

    suspend fun addTodo(title: String) {
        val todo = TodoEntity(
            id = 0, // Room generiert echt ID (0 wird ignoriert, nächste frei ID wird vergeben)
            title = title,
            isDone = false
        )
        todoDao.insertTodo(todo)
    }

    suspend fun editTodo(id: Int, newTitle: String) {
        val todo = todoDao.getTodoById(id) ?: return
        todoDao.updateTodo(todo.copy(title = newTitle))
    }
}