package com.example.searchmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.searchmovies.data.ResponseItem

@Database(entities = [ResponseItem::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun getDao(): SearchDao

    companion object {
        private var INSTANCE: SearchDatabase? = null

        fun getDatabase(context: Context): SearchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SearchDatabase::class.java,
                    "search_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}