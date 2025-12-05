package de.dhbw.heidenheim.schuetz.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.schuetz.todoapp.data.repository.TodoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
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
    fun loadTodosFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.loadTodosFromApi()

            result
                .onSuccess {
                    // Todos werden automatisch über Room-Flow aktualisiert
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown Error"
                }

            _isLoading.value = false
        }
    }
}

data class Todo(
    val id: Int,
    val title: String = "",
    val isDone: Boolean = false
)