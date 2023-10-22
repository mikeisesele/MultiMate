package com.michael.template.feature.mainscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.michael.template.core.ui.components.Spacer
import com.michael.template.core.ui.extensions.clickable
import com.michael.template.core.ui.theme.Dimens.IconSize24
import com.michael.template.core.ui.theme.Dimens.Padding16
import com.michael.template.core.ui.theme.Dimens.textSize16

@Composable
fun MenuItem(
    icon: ImageVector?,
    title: String,
    menuAction: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(Padding16)
            .clickable {
                menuAction()
            },
    ) {
        icon?.let {
            Image(
                imageVector = icon,
                contentDescription = "",
                colorFilter = ColorFilter.colorMatrix(colorMatrix = ColorMatrix()),
                modifier = Modifier.size(IconSize24),
            )
            // use Int as icon
//            Icon(
//                painter = painterResource(id = icon),
//                contentDescription = title,
//                tint = Color.White,
//                modifier = Modifier.size(IconSize24),
//            )
            Spacer(horizontal = Padding16)
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = textSize16,
            modifier = Modifier.padding(start = Padding16),
            fontWeight = FontWeight.Medium,
        )
    }
}
