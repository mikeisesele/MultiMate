package com.michael.template.feature.mainscreen.contract

import com.michael.template.core.base.contract.SideEffect
import com.michael.template.navigation.Destination

sealed class MainSideEffect : SideEffect {
    data class NavigateToDestination(val destination: Destination) : MainSideEffect()
}
