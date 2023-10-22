package com.michael.template.feature.mainscreen.contract

import com.michael.template.navigation.Destination

sealed interface MainViewAction {

    data class DestinationClicked(val destination: Destination) : MainViewAction
    object ToggleMenuVisibility : MainViewAction
}
