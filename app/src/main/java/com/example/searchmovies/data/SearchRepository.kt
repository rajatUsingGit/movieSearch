package com.example.searchmovies.data

import kotlinx.coroutines.*
import java.lang.Exception

class SearchRepository(private val remoteDatabase: WebApiService = WebApi.retrofitService) {
    suspend fun getDataFromNetwork(query: String): List<ResponseItem> {
         return withContext(Dispatchers.IO) {
             try {
                 remoteDatabase.getSearchResults(query).body()?.responses ?: emptyList()
             } catch(exception: Exception) {
                 emptyList()
             }
         }
    }
}