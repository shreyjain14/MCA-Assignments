package me.shreyjain.cia2.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getAllTasksForUser(userId: Long): Flow<List<Task>> {
        return taskDao.getAllTasksForUser(userId)
    }

    suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)
    }

    fun getTravelTasksForUser(userId: Long): Flow<List<Task>> {
        return taskDao.getTravelTasksForUser(userId)
    }
} 