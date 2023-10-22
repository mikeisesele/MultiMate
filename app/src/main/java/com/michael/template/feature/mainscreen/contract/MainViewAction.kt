package com.michael.template.feature.mainscreen.contract

import com.michael.template.navigation.Destinations

sealed interface MainViewAction {

    data class DestinationClicked(val destination: Destinations) : MainViewAction
    object ToggleMenuVisibility : MainViewAction
    object OnBackClicked : MainViewAction
}
