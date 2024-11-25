package com.example.flightsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.FlightsFromDestination
import com.example.flightsearch.ui.FlightsFromScreen
import com.example.flightsearch.ui.HomeDestination
import com.example.flightsearch.ui.HomeScreen

@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    filteredAirports: List<Airport>,
    modifier: Modifier = Modifier,
    ) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToFlightsFrom = { name, iataCode ->
                    navController.navigate("${FlightsFromDestination.route}/$name/$iataCode")
                },
                filteredAirports = filteredAirports,

            )
        }
        composable(
            route = FlightsFromDestination.routeWithArgs,
            arguments = listOf(
                navArgument(FlightsFromDestination.argName) { type = NavType.StringType },
                navArgument(FlightsFromDestination.argIataCode) { type = NavType.StringType }
            )
        ) {
            FlightsFromScreen(
                navigateBack = { navController.popBackStack() },
                navController = navController,
            )
        }
    }
}


