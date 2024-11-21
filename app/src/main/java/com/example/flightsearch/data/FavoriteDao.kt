package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query(
        """
        SELECT * FROM favorite
        ORDER BY id ASC    
        """
    )
    fun getList(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite " +
            "WHERE id = :id LIMIT 1")

    fun getFavoriteById(id: Int?): Flow<Favorite?>

}