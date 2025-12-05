package de.dhbw.heidenheim.schuetz.todoapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val id: Int,
    val title: String,
    val completed: Boolean
)