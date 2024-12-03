package com.rewtio.tugasku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewtio.tugasku.database.TugasData
import com.rewtio.tugasku.database.TugasDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val database: TugasDatabase) : ViewModel() {

    val listTugas = database.tugasDao().getTugas()

    fun addTugas(tugas: TugasData) {
        viewModelScope.launch(Dispatchers.IO) {
            database.tugasDao().insertTugas(tugas)
        }
    }

    fun editTugas(tugas: TugasData) {
        viewModelScope.launch(Dispatchers.IO) {
            database.tugasDao().updateTugas(tugas)
        }
    }

    fun deleteTugas(tugas: TugasData) {
        viewModelScope.launch(Dispatchers.IO) {
            database.tugasDao().deleteTugas(tugas)
        }
    }
}