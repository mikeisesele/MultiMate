package com.michael.template.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.michael.template.core.ui.components.CenteredColumn
import com.michael.template.core.ui.theme.Dimens
import com.michael.template.core.ui.theme.textColor

@Composable
fun HomeScreen() {
    CenteredColumn {
        Text(
            text = "Home screen",
            fontSize = Dimens.textSize24,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
    }
}

