package de.dhbw.heidenheim.schuetz.todoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {
    val todos: List<Todo> =
        listOf(Todo("Einkaufen"), Todo("Lernen"), Todo("Auto waschen"), Todo("Projekt bearbeiten"))
    private val _todoList = MutableStateFlow(todos)
    val todoList = _todoList.asStateFlow()

}

data class Todo(
    val title: String = "",
    val isDone: Boolean = false
)