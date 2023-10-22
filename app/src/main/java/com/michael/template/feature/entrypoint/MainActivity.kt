package com.michael.template.feature.entrypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.michael.template.core.base.contract.ViewEvent
import com.michael.template.core.base.model.Ignored
import com.michael.template.core.ui.extensions.collectAsEffect
import com.michael.template.core.ui.extensions.rememberStateWithLifecycle
import com.michael.template.core.ui.theme.Dimens
import com.michael.template.core.ui.theme.MultiMateTheme
import com.michael.template.core.ui.theme.dashBoardBackgroundColor
import com.michael.template.core.ui.theme.layerColor
import com.michael.template.core.ui.theme.mainScreenBackgroundColor
import com.michael.template.core.ui.theme.textColor
import com.michael.template.feature.mainscreen.MainScreenViewModel
import com.michael.template.feature.mainscreen.component.Layer
import com.michael.template.feature.mainscreen.component.MenuComponentWrapper
import com.michael.template.feature.mainscreen.contract.MainSideEffect
import com.michael.template.feature.mainscreen.contract.MainViewAction
import com.michael.template.feature.mainscreen.model.MenuAnimation
import com.michael.template.feature.mainscreen.model.MenuState
import com.michael.template.navigation.Destination
import com.michael.template.navigation.NavigationGraph
import com.michael.template.navigation.navigator
import com.michael.template.navigation.processNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

private const val LAYER_ZERO_COLOR_ALPHA = 0.90f
private const val LAYER_ONE_COLOR_ALPHA = 0.5f

private const val TRANSITION_OFFSET_X = 50f
private const val LAYER_ZERO_SCALE = 0.05f
private const val LAYER_ONE_SCALE = 0.08f

private const val TWEEN_DURATION = 300
private const val EXPANDED_MENU_SCALE = 0.7f
private const val COLLAPSED_MENU_SCALE = 1f
private const val EXPANDED_OFFSET_X = 750f
private const val EXPANDED_OFFSET_Y = 60f
private const val ALPHA_MENU_EXPANDED = 1f
private const val ALPHA_MENU_COLLAPSED = 0.5f
private const val ROUNDNESS_EXPANDED = 20
private const val MENU_OFFSET_COLLAPSED_X = -100f

@Suppress("LongMethod", "ComplexMethod")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val state by rememberStateWithLifecycle(mainScreenViewModel.state)
            val updateAnim = updateTransition(state.currentMenuState, label = "MenuState")

            subscribeToSideEffects(
                events = mainScreenViewModel::events,
                navigateToDestinations = {
                    navController.processNavigation(it)
                },
            )

            val scale = updateAnim.animateFloat(
                transitionSpec = {
                    when {
                        MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                            tween(
                                durationMillis = TWEEN_DURATION,
                                easing = LinearOutSlowInEasing,
                            )
                        }
                        MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                            tween(
                                durationMillis = TWEEN_DURATION,
                                easing = LinearOutSlowInEasing,
                            )
                        }
                        else -> snap()
                    }
                },
                label = "",
            ) {
                when (it) {
                    MenuState.EXPANDED -> EXPANDED_MENU_SCALE
                    MenuState.COLLAPSED -> COLLAPSED_MENU_SCALE
                }
            }

            val transitionOffset = updateAnim.animateOffset({
                when {
                    MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                        tween(
                            durationMillis = TWEEN_DURATION,
                            easing = LinearOutSlowInEasing,
                        )
                    }
                    MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                        tween(
                            durationMillis = TWEEN_DURATION,
                            easing = LinearOutSlowInEasing,
                        )
                    }
                    else -> snap()
                }
            }, label = "",) {
                when (it) {
                    MenuState.EXPANDED -> Offset(EXPANDED_OFFSET_X, EXPANDED_OFFSET_Y)
                    MenuState.COLLAPSED -> Offset(0f, 0f)
                }
            }

            val alphaMenu = updateAnim.animateFloat({
                when {
                    MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                        tween(durationMillis = TWEEN_DURATION)
                    }
                    MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                        tween(durationMillis = TWEEN_DURATION)
                    }
                    else -> snap()
                }
            }, label = "",) {
                when (it) {
                    MenuState.EXPANDED -> ALPHA_MENU_EXPANDED
                    MenuState.COLLAPSED -> ALPHA_MENU_COLLAPSED
                }
            }

            val roundness = updateAnim.animateDp({
                when {
                    MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                        tween(durationMillis = TWEEN_DURATION)
                    }
                    MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                        tween(durationMillis = TWEEN_DURATION)
                    }
                    else -> snap()
                }
            }, label = "",) {
                when (it) {
                    MenuState.EXPANDED -> ROUNDNESS_EXPANDED.dp
                    MenuState.COLLAPSED -> 0.dp
                }
            }

            val menuOffset = updateAnim.animateOffset({
                when {
                    MenuState.EXPANDED isTransitioningTo MenuState.COLLAPSED -> {
                        tween(
                            durationMillis = TWEEN_DURATION,
                            easing = LinearOutSlowInEasing,
                        )
                    }
                    MenuState.COLLAPSED isTransitioningTo MenuState.EXPANDED -> {
                        tween(
                            durationMillis = TWEEN_DURATION,
                            easing = LinearOutSlowInEasing,
                        )
                    }
                    else -> snap()
                }
            }, label = "",) {
                when (it) {
                    MenuState.EXPANDED -> Offset(0f, 0f)
                    MenuState.COLLAPSED -> Offset(MENU_OFFSET_COLLAPSED_X, 0f)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenBackgroundColor),
            ) {
                // Side menu
                MenuComponentWrapper(
                    menuOffset = menuOffset,
                    alphaMenu = alphaMenu.value,
                    destinations = state.screens,
                    destinationClicked = {
                        mainScreenViewModel.onViewAction(MainViewAction.DestinationClicked(it))
                    },
                )

                // Stack layer 0
                Layer(
                    scale = scale.value - LAYER_ZERO_SCALE,
                    offset = transitionOffset.value,
                    alpha = alphaMenu.value,
                    color = layerColor.copy(alpha = LAYER_ZERO_COLOR_ALPHA),
                )

                // Stack layer 1
                Layer(
                    scale = scale.value - LAYER_ONE_SCALE,
                    offset = transitionOffset.value - Offset(TRANSITION_OFFSET_X, 0f),
                    alpha = alphaMenu.value,
                    color = layerColor.copy(alpha = LAYER_ONE_COLOR_ALPHA),
                )
            }

            MultiMateTheme {
                Column {
                    if (state.isCollapsed) {
                        val title = navController
                            .currentBackStackEntryAsState()
                            .value?.destination?.route
                            ?: Destination.Home.title

                        ToolBar(
                            title = title,
                            toggleVisibility = {
                                mainScreenViewModel.onViewAction(MainViewAction.ToggleMenuVisibility)
                            },
                        )
                    }
                    BaseScreen(
                        MenuAnimation(
                            scale = scale.value,
                            transitionOffset = transitionOffset.value,
                            alphaMenu = alphaMenu.value,
                            roundness = roundness.value,
                            menuOffset = menuOffset.value,
                        ),
                    ) {
                        NavigationGraph(navController)
                    }
                }
            }
        }
    }
}

@Suppress("composableNaming")
@Composable
private fun subscribeToSideEffects(
    events: () -> Flow<ViewEvent>,
    navigateToDestinations: (Destination) -> Unit,
) {
    events().collectAsEffect { viewEvent ->
        when (viewEvent) {
            is ViewEvent.Effect -> when (val target = viewEvent.effect) {
                is MainSideEffect.NavigateToDestination -> {
                    navigator(target.destination, navigateToDestinations)
                }
            }

            else -> Ignored
        }
    }
}

@Composable
private fun ToolBar(
    title: String,
    toggleVisibility: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(color = dashBoardBackgroundColor)
            .fillMaxWidth()
            .padding(Dimens.Padding16),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            modifier = Modifier
                .size(Dimens.size24)
                .clickable { toggleVisibility() },
            colorFilter = ColorFilter.tint(textColor),
        )

        Spacer(modifier = Modifier.width(Dimens.Padding16))

        Text(
            text = title,
            fontSize = Dimens.textSize24,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
    }
}
