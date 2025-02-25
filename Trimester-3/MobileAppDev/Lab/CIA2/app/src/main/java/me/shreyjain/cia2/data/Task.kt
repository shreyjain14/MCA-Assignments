package me.shreyjain.cia2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: Long, // Store date as timestamp
    val isTravelRelated: Boolean = false,
    val userId: Long // To associate tasks with specific users
) 