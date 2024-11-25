package com.example.flightsearch.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

class FlightSearchViewModel(
    private val airportDao: AirportDao,

    ) : ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableStateFlow<String> = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState

    private val _airport: MutableStateFlow<Airport?> = MutableStateFlow(null)
    val airport: StateFlow<Airport?> = _airport

    private val _filteredAirports = MutableStateFlow<List<Airport>>(emptyList())
    val filteredAirports: StateFlow<List<Airport>> = _filteredAirports

    fun getAirportsSatisfied(searchTextState: String): Flow<List<Airport>> =
        airportDao.getAirportsSatisfied(searchTextState)

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
        Log.d("SEARCH4", searchTextState.toString())
    }

/*    init {
        viewModelScope.launch {
            combine(
                airportDao.getAirportsSatisfied(searchTextState.toString()),
                _searchTextState
            ) { airports, searchTextState ->
                airports.filter {
                    it.name.contains(searchTextState, ignoreCase = true) ||
                            it.iataCode.contains(searchTextState, ignoreCase = true)
                }
            }.collect { filteredList ->
                _filteredAirports.value = filteredList
            }
        }
    }*/

    init {
        viewModelScope.launch {
            _searchTextState
                .debounce(300) // Задержка для предотвращения слишком частых запросов
                .flatMapLatest { searchText ->
                    if (searchText.isBlank()) {
                        // Если строка пустая, возвращаем пустой список
                        MutableStateFlow(emptyList())
                    } else {
                        getAirportsSatisfied(searchText)
                    }
                }
                .collect { airports ->
                    _filteredAirports.value = airports
                }
        }
    }

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


