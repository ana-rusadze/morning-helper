package com.example.morninghelper.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Notes>

    @Query("SELECT * FROM notes WHERE nid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Notes>

    @Query(
        "SELECT * FROM notes WHERE title LIKE:title"
    )
    suspend fun findByName(title: String): Notes

    @Insert
    suspend fun insertAll(vararg notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)





}