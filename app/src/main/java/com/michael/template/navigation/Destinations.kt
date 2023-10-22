package com.michael.template.navigation

import androidx.compose.ui.graphics.vector.ImageVector

enum class Destinations(val title: String, val icon: ImageVector? = null) {
    HOME(title = "Home"),
    SETTINGS(title = "Settings")
}
