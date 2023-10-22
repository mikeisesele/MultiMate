package com.michael.template.feature.mainscreen.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
@Immutable
data class MenuAnimation(
    val scale: Float,
    val transitionOffset: Offset,
    val alphaMenu: Float,
    val roundness: Dp,
    val menuOffset: Offset,
)
