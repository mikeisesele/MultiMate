package com.michael.template.feature.mainscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.michael.template.core.ui.theme.Dimens
import kotlin.math.roundToInt

@Composable
fun Layer(
    scale: Float,
    offset: Offset,
    alpha: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .scale(scale)
            .offset {
                IntOffset(
                    offset.x.roundToInt(),
                    offset.y.roundToInt(),
                )
            }
            .background(color, shape = RoundedCornerShape(Dimens.Radius20))
            .padding(Dimens.Padding8)
            .alpha(alpha),
    )
}
