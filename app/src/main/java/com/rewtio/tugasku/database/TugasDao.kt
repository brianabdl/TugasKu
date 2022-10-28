package com.rewtio.tugasku.database


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTugas(data: TugasData)

    @Delete
    suspend fun deleteTugas(data: TugasData)

    @Update
    suspend fun updateTugas(data: TugasData)

    @Query("SELECT * FROM Tugas")
    fun getTugas(): Flow<List<TugasData>>
}