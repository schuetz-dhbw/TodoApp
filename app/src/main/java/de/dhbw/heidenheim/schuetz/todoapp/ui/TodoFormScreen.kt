package de.dhbw.heidenheim.schuetz.todoapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoFormScreen(
    initialTitle: String = "",
    isEdit: Boolean = false,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(initialTitle) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if(isEdit) "Todo bearbeiten" else "Neues Todo eingeben",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Aufgabe") }
        )
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    onSave(title)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Speichern")
        }
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Abbrechen")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TodoFormScreenPreviewNew() {
    TodoFormScreen(
        initialTitle = "",
        isEdit = false,
        onSave = {},
        onCancel = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TodoFormScreenPreviewEdit() {
    TodoFormScreen(
        initialTitle = "Einkaufen",
        isEdit = true,
        onSave = {},
        onCancel = {}
    )
}
