package me.shreyjain.cia2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.shreyjain.cia2.data.AppDatabase
import me.shreyjain.cia2.data.Task
import me.shreyjain.cia2.data.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    
    // Current user ID (set after login)
    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId: StateFlow<Long?> = _currentUserId
    
    // All tasks for the current user
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    
    // Travel-related tasks only
    private val _travelTasks = MutableStateFlow<List<Task>>(emptyList())
    val travelTasks: StateFlow<List<Task>> = _travelTasks.asStateFlow()
    
    // Task operation state
    private val _taskOperationState = MutableStateFlow<TaskOperationState>(TaskOperationState.Idle)
    val taskOperationState: StateFlow<TaskOperationState> = _taskOperationState.asStateFlow()

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
    }
    
    fun setCurrentUserId(userId: Long) {
        _currentUserId.value = userId
        loadTasks(userId)
    }
    
    private fun loadTasks(userId: Long) {
        viewModelScope.launch {
            repository.getAllTasksForUser(userId).collect { taskList ->
                _tasks.value = taskList
            }
        }
        
        viewModelScope.launch {
            repository.getTravelTasksForUser(userId).collect { travelTaskList ->
                _travelTasks.value = travelTaskList
            }
        }
    }
    
    suspend fun getTaskById(taskId: Long): Task? {
        return repository.getTaskById(taskId)
    }
    
    fun addTask(title: String, description: String, date: Long, isTravelRelated: Boolean) {
        val userId = _currentUserId.value ?: return
        
        viewModelScope.launch {
            _taskOperationState.value = TaskOperationState.Loading
            
            val task = Task(
                title = title,
                description = description,
                date = date,
                isTravelRelated = isTravelRelated,
                userId = userId
            )
            
            val taskId = repository.insertTask(task)
            
            if (taskId > 0) {
                _taskOperationState.value = TaskOperationState.Success("Task added successfully")
            } else {
                _taskOperationState.value = TaskOperationState.Error("Failed to add task")
            }
        }
    }
    
    fun updateTask(task: Task) {
        viewModelScope.launch {
            _taskOperationState.value = TaskOperationState.Loading
            
            try {
                repository.updateTask(task)
                _taskOperationState.value = TaskOperationState.Success("Task updated successfully")
            } catch (e: Exception) {
                _taskOperationState.value = TaskOperationState.Error("Failed to update task: ${e.message}")
            }
        }
    }
    
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _taskOperationState.value = TaskOperationState.Loading
            
            try {
                repository.deleteTask(task)
                _taskOperationState.value = TaskOperationState.Success("Task deleted successfully")
            } catch (e: Exception) {
                _taskOperationState.value = TaskOperationState.Error("Failed to delete task: ${e.message}")
            }
        }
    }
    
    fun resetTaskOperationState() {
        _taskOperationState.value = TaskOperationState.Idle
    }
    
    sealed class TaskOperationState {
        object Idle : TaskOperationState()
        object Loading : TaskOperationState()
        data class Success(val message: String) : TaskOperationState()
        data class Error(val message: String) : TaskOperationState()
    }
} 