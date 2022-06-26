package com.example.searchmovies.data

import android.app.Application
import com.example.searchmovies.data.local.SearchDatabase
import com.example.searchmovies.data.remote.WebApi
import com.example.searchmovies.data.remote.WebApiService
import kotlinx.coroutines.*
import java.lang.Exception

class SearchRepository(application: Application) {

    private val remoteDatabase: WebApiService = WebApi.retrofitService
    private val localDatabase: SearchDatabase = SearchDatabase.getDatabase(application)

    suspend fun getData(query: String): List<ResponseItem> {
        return withContext(Dispatchers.IO) {
            try {
                val results = remoteDatabase.getSearchResults(query)
                    .body()?.responses ?: emptyList()
                if (results.isNotEmpty()) {
                    localDatabase.getDao().insert(results)
                }
                results
            } catch(exception: Exception) {
                localDatabase.getDao().get(query)
            }
        }
    }

}