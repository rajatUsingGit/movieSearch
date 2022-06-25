package com.example.searchmovies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovies.data.ResponseItem
import com.example.searchmovies.data.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _responses: MutableStateFlow<List<ResponseItem>> = MutableStateFlow(emptyList())
    val responses: StateFlow<List<ResponseItem>> = _responses
    private val repository = SearchRepository()

    fun refreshUI(query: String) {
        viewModelScope.launch {
            _responses.value = repository.getDataFromNetwork(query)
        }
    }

}