package com.example.to_do.dp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskDM(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    var title: String?,
    @ColumnInfo
    var description: String?,
    @ColumnInfo
    var dateAsString: String?,
    @ColumnInfo
    var time: String?,
    @ColumnInfo
    var date: Long?,
    @ColumnInfo
    var isDone: Boolean?
) : Serializable