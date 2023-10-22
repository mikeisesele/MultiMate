package com.michael.template.navigation

fun navigator(destination: Destination, navigateToDestination: (Destination) -> Unit) {
    when (destination) {
        Destination.Home -> navigateToDestination(Destination.Home)
        Destination.Setting -> navigateToDestination(Destination.Setting)
        Destination.PasswordGenerator -> navigateToDestination(Destination.PasswordGenerator)
    }
}
