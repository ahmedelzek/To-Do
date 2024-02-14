package com.example.to_do.dp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    fun insert(task: TaskDM)

    @Delete
    fun delete(task: TaskDM)

    @Update
    fun update(task: TaskDM)

    @Query("SELECT * FROM TaskDM")
    fun showAllTasks(): List<TaskDM>

    @Query("SELECT * FROM TaskDM WHERE date = :date")
    fun getTasksByDate(date: Long): List<TaskDM>
}