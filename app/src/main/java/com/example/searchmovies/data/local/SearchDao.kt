package com.example.searchmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchmovies.data.ResponseItem

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: List<ResponseItem>)

    @Query("SELECT * FROM search_table WHERE title LIKE :query || '%'")
    fun get(query: String): List<ResponseItem>

}