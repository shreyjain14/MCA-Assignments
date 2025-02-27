package me.shreyjain.cia2.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import me.shreyjain.cia2.data.Task
import me.shreyjain.cia2.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel
) {
    val isNewTask = taskId == null
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userId by viewModel.currentUserId.collectAsStateWithLifecycle()
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    var isTravelRelated by remember { mutableStateOf(false) }
    var task by remember { mutableStateOf<Task?>(null) }
    
    val taskOperationState by viewModel.taskOperationState.collectAsStateWithLifecycle()
    
    // Load existing task if not a new task
    LaunchedEffect(taskId) {
        if (!isNewTask && taskId != null) {
            viewModel.getTaskById(taskId)?.let {
                task = it
                title = it.title
                description = it.description
                date = it.date
                isTravelRelated = it.isTravelRelated
            }
        }
    }
    
    // Handle task operation state changes
    LaunchedEffect(taskOperationState) {
        when (taskOperationState) {
            is TaskViewModel.TaskOperationState.Success -> {
                // Reset state and navigate back immediately
                viewModel.resetTaskOperationState()
                onNavigateBack()
                
                // The snackbar will be shown in the previous screen (all tasks/travel tasks)
                // This prevents the snackbar from blocking UI interaction in this screen
            }
            is TaskViewModel.TaskOperationState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar((taskOperationState as TaskViewModel.TaskOperationState.Error).message)
                    viewModel.resetTaskOperationState()
                }
            }
            else -> {}
        }
    }
    
    // Date picker
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isNewTask) "Add Task" else "Edit Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (!isNewTask) {
                        IconButton(
                            onClick = {
                                task?.let { viewModel.deleteTask(it) }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Date picker field
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(date))
            
            OutlinedTextField(
                value = formattedDate,
                onValueChange = { },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isTravelRelated,
                    onCheckedChange = { isTravelRelated = it }
                )
                
                Text(
                    text = "Travel Related",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    if (isNewTask) {
                        // Add new task
                        userId?.let { viewModel.addTask(title, description, date, isTravelRelated) }
                    } else {
                        // Update existing task
                        task?.let {
                            val updatedTask = it.copy(
                                title = title,
                                description = description,
                                date = date,
                                isTravelRelated = isTravelRelated
                            )
                            viewModel.updateTask(updatedTask)
                        }
                    }
                },
                enabled = title.isNotBlank() && userId != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isNewTask) "Add Task" else "Update Task")
            }
        }
    }
} 