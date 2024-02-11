package com.example.to_do.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskDM::class], version = 1, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "todo database"
        private var database: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext, TaskDatabase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            }
            return database!!
        }
    }
}