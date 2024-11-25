package com.example.flightsearch.data

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface AirportDao {
    @Query(
        """
        SELECT * FROM airport
        ORDER BY name ASC    
        """
    )
    fun getList(): Flow<List<Airport>>

/*    @Query("SELECT * from airport WHERE iata_code= :searchTextState OR name LIKE '%' " +
            "|| :searchTextState || '%' ")*/

    @Query("SELECT * FROM airport\n" +
            "WHERE LOWER(iata_code) LIKE '%' || LOWER(:searchTextState) || '%'\n" +
            "   OR LOWER(name) LIKE '%' || LOWER(:searchTextState) || '%'")
    fun getAirportsSatisfied(searchTextState: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport " +
            "WHERE id = :id LIMIT 1")

    fun getAirportById(id: Int?): Flow<Airport?>

}



