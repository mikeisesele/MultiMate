package com.michael.template.feature.mainscreen.contract

import androidx.navigation.NavHostController
import com.michael.template.navigation.Destinations

sealed interface MainViewAction {

    data class DestinationClicked(val destination: Destinations) : MainViewAction
    object ToggleMenuVisibility : MainViewAction
    object OnBackClicked : MainViewAction
    data class ProcessNavigation(
        val navHostController: NavHostController,
        val destination: Destinations,
    ) : MainViewAction
}
