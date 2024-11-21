package com.example.flightsearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.navigation.NavigationDestination


object FlightsFromDestination : NavigationDestination {
    override val route = "flights"
    const val argName = "name"
    const val argIataCode = "iataCode"
    override val titleRes = R.string.flights_from
    val routeWithArgs = "$route/{$argName}/{$argIataCode}"
}

@Composable
fun FlightsFromScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory),
    navController: NavController
    ) {
    val airports = viewModel.getList().collectAsState(initial = emptyList())
    val name = navController.currentBackStackEntry?.arguments?.getString(FlightsFromDestination.argName)
    val iataCode = navController.currentBackStackEntry?.arguments?.getString(FlightsFromDestination.argIataCode)

    FlightsList(
        contentPadding = contentPadding(),
        modifier = Modifier,
        airports = airports.value,
        name = name.toString(),
        iataCode = iataCode.toString()
    )
}

@Composable
fun FlightsList(
    airports: List<Airport>,
    name: String,
    iataCode: String,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(Color.LightGray),
        contentPadding = contentPadding,
    ) {
        items(airports) { airport ->
            FlightItem(
                airport = airport,
                name = name,
                iataCode = iataCode

            )
        }
    }
}

@Composable
fun FlightItem(
    airport: Airport,
    name: String,
    iataCode: String,
    modifier: Modifier = Modifier.padding(8.dp)
) {
    Card(modifier = modifier) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.depart),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Row(modifier = modifier) {
                Text(
                    text = iataCode,
                    modifier = modifier.padding(end = 32.dp, bottom = 8.dp)
                )
                Text(text = name)
            }

            Text(
                text = stringResource(R.string.arrive),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Row() {
                Text(text = airport.iataCode, modifier = modifier.padding(end = 32.dp))
                Text(text = airport.name)
            }
        }
    }
}

/*@Composable
fun FlightsFromScreenPreview() {
    val airports = listOf(
        Airport(id = 2, name = "Vnukovo", iataCode = "VNU", passengers = 234233),
        Airport(id = 3, name = "Sheremetyevo", iataCode = "SVO", passengers = 412341)
    )

    FlightsList(
        departureAirportUiState = Airport(id = 1, name = "Pushkin", iataCode = "PUS", passengers = 124455),
        airports = airports,
        contentPadding = PaddingValues(16.dp)
    )
}*/
