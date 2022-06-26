package com.example.searchmovies.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_table")
data class ResponseItem(
    @PrimaryKey @ColumnInfo @SerializedName("id") val id: Int,
    @ColumnInfo @SerializedName("title") val title: String,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val posterPath: String
    )
