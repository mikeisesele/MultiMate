package com.michael.template.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.michael.template.feature.home.HomeScreen
import com.michael.template.feature.settings.Settings

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME.title,
    ) {
        composable(route = Destinations.HOME.title) {
            HomeScreen()
        }
        composable(route = Destinations.SETTINGS.title) {
            Settings()
        }
    }
}
