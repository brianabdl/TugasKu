package com.rewtio.tugasku.ui.viewmodel

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rewtio.tugasku.database.TugasData
import com.rewtio.tugasku.database.TugasDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _listTugas = MutableStateFlow(emptyList<TugasData>())
    val listTugas: StateFlow<List<TugasData>>
        get() = _listTugas.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val db = TugasDatabase.getInstance(getApplication())
            val data = db.tugasDao().getTugas()
            withContext(Dispatchers.Main) {
                _listTugas.emit(data)
            }
        }
    }

    fun addTugas(tugas: TugasData) {
        viewModelScope.launch {
            _listTugas.update {
                val copy = it.toMutableList()
                copy.add(tugas)
                copy
            }
            withContext(Dispatchers.IO) {
                TugasDatabase.getInstance(getApplication()).tugasDao().insertTugas(tugas)
            }
        }
    }

    fun editTugas(tugas: TugasData) {
        viewModelScope.launch {
            _listTugas.update {
                val newCopy = it.toMutableList()
                newCopy.find { tugasMu -> tugasMu.id == tugas.id }?.let { tugasLama ->
                    tugasLama.status = tugas.status
                    tugasLama.judul = tugas.judul
                    tugasLama.mapel = tugas.mapel
                    tugasLama.deskripsi = tugas.deskripsi
                    tugasLama.dibuat = tugas.dibuat
                    tugasLama.deadline = tugas.deadline
                }
                newCopy
            }
            withContext(Dispatchers.IO) {
                TugasDatabase.getInstance(getApplication()).tugasDao().updateTugas(tugas)
            }
        }
    }

    fun deleteTugas(tugas: TugasData) {
        viewModelScope.launch {
            _listTugas.update { tugasData ->
                val newCopy = tugasData.toMutableList()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    newCopy.removeIf {
                        it.id == tugas.id
                    }
                } else {
                    newCopy.forEachIndexed { index, now ->
                        if ((now.id == tugas.id)) {
                            newCopy.removeAt(index)
                        }
                    }
                }
                newCopy
            }

            withContext(Dispatchers.IO) {
                TugasDatabase.getInstance(getApplication()).tugasDao().deleteTugas(tugas)
            }
        }
    }
}