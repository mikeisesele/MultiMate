package com.michael.template.feature.mainscreen

import com.michael.template.core.base.contract.BaseViewModel
import com.michael.template.core.base.contract.ViewEvent
import com.michael.template.core.base.model.toImmutableList
import com.michael.template.core.base.providers.DispatcherProvider
import com.michael.template.feature.mainscreen.contract.MainSideEffect
import com.michael.template.feature.mainscreen.contract.MainState
import com.michael.template.feature.mainscreen.contract.MainViewAction
import com.michael.template.feature.mainscreen.model.MenuState
import com.michael.template.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel<MainState, MainViewAction>(
    MainState.initialState,
    dispatcherProvider,
) {

    init {
        updateState { state ->
            state.copy(
                screens = Destinations.values().toList().toImmutableList(),
            )
        }
    }
    override fun onViewAction(viewAction: MainViewAction) {
        when (viewAction) {
            MainViewAction.ToggleMenuVisibility -> toggleMenu()
            MainViewAction.OnBackClicked -> navigate(Destinations.HOME)
            is MainViewAction.DestinationClicked -> navigate(viewAction.destination)
        }
    }

    private fun navigate(destination: Destinations) {
        updateState { state ->
            state.copy(
                currentMenuState = MenuState.COLLAPSED,
                currentDestination = destination,
            )
        }
        dispatchViewEvent(
            ViewEvent.Effect(MainSideEffect.NavigateToDestination(destination)),
        )
    }

    private fun toggleMenu() {
        updateState { state ->
            state.copy(
                currentMenuState = when (state.currentMenuState) {
                    MenuState.EXPANDED -> MenuState.COLLAPSED
                    MenuState.COLLAPSED -> MenuState.EXPANDED
                },
            )
        }
    }
}
