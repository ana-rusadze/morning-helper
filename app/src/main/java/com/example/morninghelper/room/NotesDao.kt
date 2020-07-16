package com.example.morninghelper.room

import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Notes>

    @Query("SELECT * FROM notes WHERE nid IN (:noteIds)")
    suspend fun loadAllByIds(noteIds: IntArray): List<Notes>

    @Query(
        "SELECT * FROM notes WHERE title LIKE:title"
    )
    suspend fun findByName(title: String): Notes

    @Insert
    suspend fun insertAll(vararg notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Update
   suspend fun updateNotes(vararg notes: Notes)

//
//    @Query("UPDATE SET title WHERE id=:id")
//    fun UpdateColumnById(text: String?, id: Int)
//
//    @Query("SELECT * FROM notes WHERE nid IN (:nId)")
//    suspend fun selectByID(nId:Int):Notes

}