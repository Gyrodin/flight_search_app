package com.example.flightsearch.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.R
import com.example.flightsearch.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToFlightsFrom: (String, String) -> Unit,  // Принимаем ID аэропорта
    flightSearchViewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    // Получаем список аэропортов и состояние поиска
    val airports by flightSearchViewModel.getList().collectAsState(emptyList())
    //val searchTextState by flightSearchViewModel.searchTextState
    val searchTextState = flightSearchViewModel.searchTextState.value

/*    val filteredAirports = remember(searchTextState, airports) {
        airports.filter {
            it.name.contains(searchTextState, ignoreCase = true) ||
                    it.iataCode.contains(searchTextState, ignoreCase = true)
        }
    }*/

    // Лог результатов фильтрации
/*    LaunchedEffect(filteredAirports) {
        println("Filtered Airports: ${filteredAirports.map { it.name }}")
        println("SearchTextState: $searchTextState")
    }*/

    // Отображаем список аэропортов
    AirportList(
        //airports = filteredAirports,
        airports = airports,
        onAirportClick = { name, iataCode ->
            navigateToFlightsFrom(name, iataCode)  // Передаем ID выбранного аэропорта
        },
        searchTextState = searchTextState,
        modifier = Modifier,
    )
}

@Composable
private fun AirportList(
    airports: List<Airport>,
    onAirportClick: (String, String) -> Unit,  // Принимаем Airport, чтобы передать его в onAirportClick
    searchTextState: String,
    modifier: Modifier = Modifier
) {
    // Фильтруем аэропорты по тексту поиска
    val filteredAirports = airports.filter {
        it.name.contains(searchTextState, ignoreCase = true) ||
                it.iataCode.contains(searchTextState, ignoreCase = true)
    }
    Log.d("SEARCH3", searchTextState)

/*    LaunchedEffect(filteredAirports) {
        println("Filtered Airports: ${filteredAirports.map { it.name }}")
        println("SearchTextState: $searchTextState")
    }*/

    LazyColumn(
        modifier = modifier.background(Color.LightGray),
        contentPadding = contentPadding()
    ) {
        items(
            items = filteredAirports
            //items = airports
        ) { airport ->
            AirportItem(
                modifier = Modifier.clickable { onAirportClick(airport.name, airport.iataCode) },
                name = airport.name,
                iataCode = airport.iataCode // Передаем airport в onAirportClick
            )
        }
    }
}

@Composable
fun AirportItem(
    name: String,
    iataCode: String,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    // Отображаем каждый аэропорт в виде карточки
    Card(modifier = Modifier.padding(12.dp)) {
        Row(modifier = modifier.padding(12.dp)) {
            Text(
                text = iataCode,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.size(16.dp))
            Text(
                text = name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirportItemPreview() {
    // Пример отображения одного аэропорта
    AirportItem(
        name = "Kurchatov",
        iataCode = "KUR"
    )
}
