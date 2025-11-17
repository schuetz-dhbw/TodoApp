package de.dhbw.heidenheim.schuetz.todoapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dhbw.heidenheim.schuetz.todoapp.Todo

@Composable
fun TodoListScreen(
    todos: List<Todo>,
    onToggle: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = onAddClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Neues Todo hinzufügen")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (todos.isEmpty()) {
                item {
                    Text(
                        text = "Keine Einträge vorhanden",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.Gray
                    )
                }
            } else {
                items(todos, key = { it.id }) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggle = { onToggle(todo.id) },
                        onDelete = { onDelete(todo.id) },
                        onClick = { onItemClick(todo.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    TodoListScreen(
        todos = listOf(
            Todo(1, "Einkaufen", false),
            Todo(2, "Lernen", true),
            Todo(3, "Sport", false)
        ),
        onToggle = {},
        onDelete = {},
        onItemClick = {},
        onAddClick = {}
    )
}