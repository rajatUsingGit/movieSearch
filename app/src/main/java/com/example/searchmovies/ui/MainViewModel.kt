package com.example.searchmovies.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    fun getDataFromNetwork(query: String): Flow<String> {
        return flow {
            delay(500)
            emit(query)
        }
    }

    fun getDataFromLocalDb(query: String): Flow<String> {
        return flow {
            delay(500)
            emit(query)
        }
    }

}