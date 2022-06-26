package com.example.searchmovies.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovies.data.ResponseItem
import com.example.searchmovies.data.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val query: MutableStateFlow<String> = MutableStateFlow("")
    private val _responses: MutableStateFlow<List<ResponseItem>> =
        MutableStateFlow(emptyList())
    val responses: StateFlow<List<ResponseItem>> = _responses
    private val mRepository = SearchRepository(application)

    fun onNewSearch() {
        viewModelScope.launch {
            _responses.value = mRepository.getData(query.value)
        }
    }

}