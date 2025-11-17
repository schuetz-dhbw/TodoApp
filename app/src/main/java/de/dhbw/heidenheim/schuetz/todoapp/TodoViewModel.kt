package de.dhbw.heidenheim.schuetz.todoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {
    private var nextId = 1
    private val _todoList: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    val todoList = _todoList.asStateFlow()

    fun toggleTodo(id: Int) {
        _todoList.value = _todoList.value.map {
            if (it.id == id) it.copy(isDone = !it.isDone)
            else it
        }
    }

    fun addTodo(title: String) {
        _todoList.value = _todoList.value + Todo(nextId++, title)
    }

    fun deleteTodo(id: Int) {
        _todoList.value = _todoList.value.filter { it.id != id }
    }

    fun updateTodo(id: Int, newTitle: String) {
        _todoList.value = _todoList.value.map {
            if (it.id == id) it.copy(title = newTitle)
            else it
        }
    }
}

data class Todo(
    val id: Int,
    val title: String = "",
    val isDone: Boolean = false
)