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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import de.dhbw.heidenheim.schuetz.todoapp.ui.AddTodoRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.AllTodosRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.BottomNavigationBar
import de.dhbw.heidenheim.schuetz.todoapp.ui.DoneTodosRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.EditTodoRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.FilterType
import de.dhbw.heidenheim.schuetz.todoapp.ui.OpenTodosRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.TodoFormScreen
import de.dhbw.heidenheim.schuetz.todoapp.ui.TodoListRoute
import de.dhbw.heidenheim.schuetz.todoapp.ui.TodoListScreen
import de.dhbw.heidenheim.schuetz.todoapp.ui.theme.ToDoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                val viewModel: TodoViewModel = hiltViewModel()
                val todos by viewModel.todoList.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Bottom Nav nur auf Haupt-Screens, nicht auf Add/Edit
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        val showBottomBar = currentRoute?.contains("AddTodoRoute") == false && !currentRoute.contains("EditTodoRoute")

                        if (showBottomBar) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                    ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AllTodosRoute,
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        composable<AllTodosRoute> {
                            TodoListScreen(
                                todos = todos,
                                filterType = FilterType.ALL,
                                onToggle = { id -> viewModel.toggleTodo(id) },
                                onDelete = { id -> viewModel.deleteTodo(id) },
                                onItemClick = { id -> navController.navigate(EditTodoRoute(todoId = id)) },
                                onAddClick = { navController.navigate(AddTodoRoute) }
                            )
                        }
                        composable<OpenTodosRoute> {
                            TodoListScreen(
                                todos = todos,
                                filterType = FilterType.OPEN,
                                onToggle = { id -> viewModel.toggleTodo(id) },
                                onDelete = { id -> viewModel.deleteTodo(id) },
                                onItemClick = { id -> navController.navigate(EditTodoRoute(todoId = id)) },
                                onAddClick = { navController.navigate(AddTodoRoute) }
                            )
                        }
                        composable<DoneTodosRoute> {
                            TodoListScreen(
                                todos = todos,
                                filterType = FilterType.DONE,
                                onToggle = { id -> viewModel.toggleTodo(id) },
                                onDelete = { id -> viewModel.deleteTodo(id) },
                                onItemClick = { id -> navController.navigate(EditTodoRoute(todoId = id)) },
                                onAddClick = { navController.navigate(AddTodoRoute) }
                            )
                        }
                        composable<AddTodoRoute> {
                            TodoFormScreen(
                                initialTitle = "",
                                isEdit = false,
                                onSave = { title ->
                                    viewModel.addTodo(title)
                                    navController.popBackStack()
                                },
                                onCancel = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable<EditTodoRoute> { backStackEntry ->
                            val editRoute = backStackEntry.toRoute<EditTodoRoute>()
                            val todo = todos.find { it.id == editRoute.todoId }
                            todo?.let {
                                TodoFormScreen(
                                    initialTitle = it.title,
                                    isEdit = true,
                                    onSave = { newTitle ->
                                        viewModel.editTodo(it.id, newTitle)
                                        navController.popBackStack()
                                    },
                                    onCancel = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

