package com.example.flightsearch.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class FlightSearchViewModel(
    private val airportDao: AirportDao,
 
): ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableStateFlow<String> = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState

    private val _airport: MutableStateFlow<Airport?> = MutableStateFlow(null)
    val airport: StateFlow<Airport?> = _airport

    private val _filteredAirports = MutableStateFlow<List<Airport>>(emptyList())
    val filteredAirports: StateFlow<List<Airport>> = _filteredAirports

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }
    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
        Log.d("SEARCH4", searchTextState.toString())
    }

    init {
        viewModelScope.launch {
            combine(
                airportDao.getList(),  // Исходный список аэропортов
                _searchTextState       // Текущий текст поиска
            ) { airports, query ->
                airports.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.iataCode.contains(query, ignoreCase = true)
                }
            }.collect { filteredList ->
                _filteredAirports.value = filteredList
            }
        }
    }
    // Get example airport
    fun getList(): Flow<List<Airport>> = airportDao.getList()

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    airportDao = application.database.airportDao(),
                )
            }
        }
    }
}

