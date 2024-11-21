package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return Instance?:synchronized(this) {
                Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}