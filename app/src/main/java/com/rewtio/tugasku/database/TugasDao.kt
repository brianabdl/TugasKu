package com.rewtio.tugasku.database

import androidx.room.*

@Dao
interface TugasDao {

    @Insert
    suspend fun insertTugas(data: TugasData)

    @Delete
    suspend fun deleteTugas(data: TugasData)

    @Update
    suspend fun updateTugas(data: TugasData)

    @Query("SELECT * FROM Tugas")
    suspend fun getTugas(): List<TugasData>
}