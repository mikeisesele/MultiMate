package com.michael.template.navigation

fun navigator(destination: Destinations, navigateToDestination: (Destinations) -> Unit) {
    when (destination) {
        Destinations.HOME -> navigateToDestination(Destinations.HOME)
        Destinations.SETTINGS -> navigateToDestination(Destinations.SETTINGS)
    }
}
