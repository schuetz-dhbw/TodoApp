package de.dhbw.heidenheim.schuetz.todoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {
    // Hier für Demonstration: Temporäre Liste mit Todos + manueller Id
    val todos: List<Todo> =
        listOf(
            Todo(1, "Einkaufen"),
            Todo(2, "Lernen"),
            Todo(3, "Auto waschen"),
            Todo(4, "Projekt bearbeiten")
        )
    private var nextId = 5
    private val _todoList = MutableStateFlow(todos)
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

}

data class Todo(
    val id: Int,
    val title: String = "",
    val isDone: Boolean = false
)