package de.dhbw.heidenheim.schuetz.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dhbw.heidenheim.schuetz.todoapp.ui.TodoListScreen
import de.dhbw.heidenheim.schuetz.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                val viewModel: TodoViewModel = viewModel()
                val todos by viewModel.todoList.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoListScreen(
                        todos = todos,
                        onToggle = { id -> viewModel.toggleTodo(id) },
                        onDelete = { id -> viewModel.deleteTodo(id) },
                        onItemClick = { id -> /* to be done */ },
                        onAddClick = { /* to be done */ },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

