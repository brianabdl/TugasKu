package com.rewtio.tugasku.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTugas(data: TugasData)

    @Delete
    suspend fun deleteTugas(data: TugasData)

    @Update
    suspend fun updateTugas(data: TugasData)

    @Query("SELECT * FROM tugas")
    fun getTugas(): Flow<List<TugasData>>
}