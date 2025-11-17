package de.dhbw.heidenheim.schuetz.todoapp.ui

import android.R.attr.contentDescription
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.serialization.Serializable

@Serializable
object TodoListRoute

@Serializable
object AddTodoRoute

@Serializable
data class EditTodoRoute(val todoId: Int)

// Bottom Nav Screens
@Serializable
object AllTodosRoute

@Serializable
object OpenTodosRoute

@Serializable
object DoneTodosRoute

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = null) },
            label = { Text(text = "Alle") },
            selected = currentRoute?.contains("AllTodosRoute") == true,
            onClick = {
                navController.navigate(AllTodosRoute) {
                    popUpTo(AllTodosRoute) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        // Offene Todos
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = { Text("Offen") },
            selected = currentRoute?.contains("OpenTodosRoute") == true,
            onClick = {
                navController.navigate(OpenTodosRoute) {
                    popUpTo(AllTodosRoute) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // Erledigte Todos
        NavigationBarItem(
            icon = { Icon(Icons.Default.Check, contentDescription = null) },
            label = { Text("Erledigt") },
            selected = currentRoute?.contains("DoneTodosRoute") == true,
            onClick = {
                navController.navigate(DoneTodosRoute) {
                    popUpTo(AllTodosRoute) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}