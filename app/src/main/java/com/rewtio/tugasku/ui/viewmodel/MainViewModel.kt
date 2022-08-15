package com.rewtio.tugasku.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rewtio.tugasku.Status
import com.rewtio.tugasku.TugasData
import com.rewtio.tugasku.dataStore
import com.rewtio.tugasku.preferences.TugasPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val listTugas = mutableStateListOf<TugasData>()

    private val preferences = getApplication<Application>().dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(1000) // let animation run

            preferences.first().let { preferences ->
                preferences[TugasPref.KEY_LIST_TUGAS]?.forEach { id ->
                    preferences[stringSetPreferencesKey("tugas_$id")]?.let { tugasStr ->
                        val list = tugasStr.toList()

                        val tugasData = TugasData(
                            id.toInt(), Status.valueOf(list[0]),
                            list[1], list[2], list[3], list[4], list[5]
                        )

                        if (!listTugas.any { it.id == tugasData.id }) {
                            listTugas.add(tugasData)
                        }
                    }
                }
            }

            _isRefreshing.emit(false)
        }
    }

    fun addTugas(tugas: TugasData) {
        listTugas.add(tugas)
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[TugasPref.KEY_LIST_TUGAS] =
                    listTugas.map { it.id.toString() }.toSet()
                preferences[stringSetPreferencesKey("tugas_${tugas.id}")] = setOf(
                    tugas.status.name, tugas.judul,
                    tugas.mapel, tugas.deskripsi,
                    tugas.dibuat, tugas.deadline
                )
            }
        }
    }

    fun editTugas(tugas: TugasData) {
        listTugas.find { it.id == tugas.id }?.let {
            it.status = tugas.status
            it.judul = tugas.judul
            it.mapel = tugas.mapel
            it.deskripsi = tugas.deskripsi
            it.dibuat = tugas.dibuat
            it.deadline = tugas.deadline
        }
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[stringSetPreferencesKey("tugas_${tugas.id}")] = setOf(
                    tugas.status.name, tugas.judul,
                    tugas.mapel, tugas.deskripsi,
                    tugas.dibuat, tugas.deadline
                )
            }
        }
    }

    fun deleteTugas(tugas: TugasData) {
        listTugas.remove(tugas)
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[TugasPref.KEY_LIST_TUGAS] = listTugas.map { it.id.toString() }.toSet()
                preferences.remove(stringSetPreferencesKey("tugas_${tugas.id}"))
            }
        }
    }
}