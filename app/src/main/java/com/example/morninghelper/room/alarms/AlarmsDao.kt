package com.example.morninghelper.room.alarms

import androidx.room.*

@Dao
interface AlarmsDao {
    @Query("SELECT * FROM alarms")
    suspend fun getAll(): List<Alarms>

    @Query("SELECT * FROM alarms WHERE aid IN (:alarmsId)")
    suspend fun loadAllByIds(alarmsId: IntArray): List<Alarms>

    @Insert
    suspend fun insertAll(vararg alarms: Alarms)

    @Delete
    suspend fun delete(alarms: Alarms)

    @Update
    suspend fun updateNotes(vararg alarms: Alarms)
}