package com.example.morninghelper.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.morninghelper.room.alarms.Alarms
import com.example.morninghelper.room.alarms.AlarmsDao

@Database(entities = [Notes::class, Alarms::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun alarmsDao(): AlarmsDao

}