package com.example.searchmovies.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovies.data.ResponseItem
import com.example.searchmovies.data.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _responses: MutableStateFlow<List<ResponseItem>> = MutableStateFlow(emptyList())
    val responses: StateFlow<List<ResponseItem>> = _responses
    private val repository = SearchRepository(application)

    fun onNewSearch(query: String) {
        viewModelScope.launch {
            _responses.value = repository.getData(query)
        }
    }

}