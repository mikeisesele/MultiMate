package com.michael.template.feature.mainscreen.contract

import com.michael.template.core.base.contract.BaseState
import com.michael.template.core.base.model.MessageState
import com.michael.template.core.base.util.ImmutableList
import com.michael.template.feature.mainscreen.model.MenuState
import com.michael.template.navigation.Destination
import com.michael.template.navigation.destinations

data class MainState(
    override val isLoading: Boolean = false,
    override val errorState: MessageState? = null,
    val currentMenuState: MenuState = MenuState.COLLAPSED,
    val screens: ImmutableList<Destination> = destinations,
) : BaseState {
    companion object {
        val initialState = MainState()
    }

    val isCollapsed = currentMenuState == MenuState.COLLAPSED
}
