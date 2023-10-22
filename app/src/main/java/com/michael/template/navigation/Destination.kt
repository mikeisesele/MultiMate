package com.michael.template.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.michael.template.core.base.util.ImmutableList
import com.michael.template.core.base.util.immutableListOf

sealed class Destination(val title: String, val icon: ImageVector? = null) {
    object Home : Destination("Home")
    object Setting : Destination("Setting")
    object PasswordGenerator : Destination("Password")
}

val destinations: ImmutableList<Destination> = immutableListOf(
    Destination.Home,
    Destination.Setting,
    Destination.PasswordGenerator,
)
