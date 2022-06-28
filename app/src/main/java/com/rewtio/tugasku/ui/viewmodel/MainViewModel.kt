package com.rewtio.tugasku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewtio.tugasku.TugasData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    //  list tugas
    private val _listTugas = MutableStateFlow<List<TugasData>>(emptyList())
    val listTugas = _listTugas.asStateFlow()

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(1000)
            _isRefreshing.emit(false)
        }
    }

    fun addTugas(tugas: TugasData) {
        viewModelScope.launch {
            _listTugas.emit(_listTugas.value.toMutableList().apply {
                add(tugas)
            })
        }
    }
}