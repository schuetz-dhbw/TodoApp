package de.dhbw.heidenheim.schuetz.todoapp

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.schuetz.todoapp.data.repository.TodoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    val todoList: StateFlow<List<Todo>> = repository.getAllTodos()
        .map { todoEntities ->
            todoEntities.map { entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    isDone = entity.isDone
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleTodo(id: Int) {
        viewModelScope.launch {
            repository.toggleTodo(id)
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(title)
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.deleteTodo(id)
        }
    }

    fun editTodo(id: Int, newTitle: String) {
        viewModelScope.launch {
            repository.editTodo(id, newTitle)
        }
    }
}

data class Todo(
    val id: Int,
    val title: String = "",
    val isDone: Boolean = false
)