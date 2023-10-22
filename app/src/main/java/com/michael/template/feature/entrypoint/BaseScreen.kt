package com.michael.template.feature.entrypoint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntOffset
import com.michael.template.core.ui.theme.dashBoardBackgroundColor
import com.michael.template.feature.mainscreen.model.MenuAnimation

@Composable
fun BaseScreen(
    menuAnimation: MenuAnimation,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scale(menuAnimation.scale)
            .offset {
                IntOffset(
                    menuAnimation.transitionOffset.x.toInt(),
                    menuAnimation.transitionOffset.y.toInt(),
                )
            }
            .clip(shape = RoundedCornerShape(menuAnimation.roundness))
            .background(color = dashBoardBackgroundColor),
    ) {
        content()
    }
}
