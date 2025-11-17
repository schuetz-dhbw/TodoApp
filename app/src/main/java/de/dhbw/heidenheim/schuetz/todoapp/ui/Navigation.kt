package de.dhbw.heidenheim.schuetz.todoapp.ui

import kotlinx.serialization.Serializable

@Serializable
object TodoListRoute

@Serializable
object AddTodoRoute

@Serializable
data class EditTodoRoute(val todoId: Int)