package de.dhbw.heidenheim.schuetz.todoapp.data.repository

import de.dhbw.heidenheim.schuetz.todoapp.data.remote.TodoApi
import de.dhbw.heidenheim.schuetz.todoapp.Todo
import de.dhbw.heidenheim.schuetz.todoapp.data.local.TodoDao
import de.dhbw.heidenheim.schuetz.todoapp.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val todoApi: TodoApi
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

    suspend fun loadTodosFromApi(): Result<List<Todo>> {
        return try {
            val todoDtos = todoApi.getTodos()

            // Mapping: DTO -> Domain Model
            val todos = todoDtos.map { todoDto ->
                Todo(
                    id = todoDto.id,
                    title = todoDto.title,
                    isDone = todoDto.completed
                )
            }

            // Optional: In lokale DB speichern
            todos.forEach { todo ->
                todoDao.insertTodo(
                    TodoEntity(
                        id = todo.id,
                        title = todo.title,
                        isDone = todo.isDone
                    )
                )
            }

            Result.success(todos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}