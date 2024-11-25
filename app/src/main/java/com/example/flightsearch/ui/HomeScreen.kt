package com.example.flightsearch.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToFlightsFrom: (String, String) -> Unit,
    filteredAirports: List<Airport>,
    flightSearchViewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    val searchTextState by flightSearchViewModel.searchTextState.collectAsState()

    for (airport in filteredAirports){
        Log.d("HomeScreen","name: ${airport.name} and iata_code: ${airport.iataCode}")
    }

    AirportList(
        airports = filteredAirports,
        onAirportClick = { name, iataCode ->
            navigateToFlightsFrom(name, iataCode)
        },
        searchTextState = searchTextState,
        modifier = Modifier,
    )
}

@Composable
private fun AirportList(
    airports: List<Airport>,
    onAirportClick: (String, String) -> Unit,
    searchTextState: String,
    modifier: Modifier = Modifier
) {
    Log.d("SEARCH3", searchTextState)
    Log.d("FilteredAirports", "Filtered list: ${airports.map { it.name }}")
    LazyColumn(
        modifier = modifier.background(Color.LightGray),
        contentPadding = contentPadding()
    ) {
        items(
            items = airports
        ) { airport ->
            AirportItem(
                modifier = Modifier.clickable { onAirportClick(airport.name, airport.iataCode) },
                name = airport.name,
                iataCode = airport.iataCode
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

    AirportItem(
        name = "Kurchatov",
        iataCode = "KUR"
    )
}
