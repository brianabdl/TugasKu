package com.rewtio.tugasku.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TugasData::class], version = 2)
abstract class TugasDatabase : RoomDatabase() {
    abstract fun tugasDao(): TugasDao

    companion object {

        @Volatile
        private var INSTANCE: TugasDatabase? = null

        fun getInstance(context: Context): TugasDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TugasDatabase::class.java, "list_tugas.db"
            ).build()
    }
}