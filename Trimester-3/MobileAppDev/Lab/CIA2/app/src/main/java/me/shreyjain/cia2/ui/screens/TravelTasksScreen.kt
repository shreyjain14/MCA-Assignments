package me.shreyjain.cia2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.shreyjain.cia2.viewmodel.TaskViewModel
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelTasksScreen(
    onAddTask: () -> Unit,
    onTaskClick: (Long) -> Unit,
    viewModel: TaskViewModel
) {
    val travelTasks by viewModel.travelTasks.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Show snackbar for task operations
    val taskOperationState by viewModel.taskOperationState.collectAsStateWithLifecycle()
    LaunchedEffect(taskOperationState) {
        when (taskOperationState) {
            is TaskViewModel.TaskOperationState.Success -> {
                snackbarHostState.showSnackbar(
                    (taskOperationState as TaskViewModel.TaskOperationState.Success).message,
                    duration = SnackbarDuration.Short // Make it short to avoid blocking interaction too long
                )
                viewModel.resetTaskOperationState()
            }
            is TaskViewModel.TaskOperationState.Error -> {
                snackbarHostState.showSnackbar(
                    (taskOperationState as TaskViewModel.TaskOperationState.Error).message,
                    duration = SnackbarDuration.Short
                )
                viewModel.resetTaskOperationState()
            }
            else -> {}
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Tasks") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        if (travelTasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No travel tasks yet. Click + to add a travel task.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(travelTasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onTaskClick(task.id) }
                    )
                }
            }
        }
    }
} 