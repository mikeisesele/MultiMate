package com.michael.template.feature.mainscreen

import com.michael.template.core.base.contract.BaseViewModel
import com.michael.template.core.base.contract.ViewEvent
import com.michael.template.core.base.providers.DispatcherProvider
import com.michael.template.feature.mainscreen.contract.MainSideEffect
import com.michael.template.feature.mainscreen.contract.MainState
import com.michael.template.feature.mainscreen.contract.MainViewAction
import com.michael.template.feature.mainscreen.model.MenuState
import com.michael.template.navigation.Destination
import com.michael.template.navigation.destinations
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
                screens = destinations,
            )
        }
    }
    override fun onViewAction(viewAction: MainViewAction) {
        when (viewAction) {
            MainViewAction.ToggleMenuVisibility -> toggleMenu()
            is MainViewAction.DestinationClicked -> navigate(viewAction.destination)
        }
    }

    private fun navigate(destination: Destination) {
        updateState { state ->
            state.copy(
                currentMenuState = MenuState.COLLAPSED,
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
