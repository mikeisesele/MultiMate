package com.michael.template.feature.mainscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.michael.template.R
import com.michael.template.core.base.util.ImmutableList
import com.michael.template.core.ui.components.Spacer
import com.michael.template.core.ui.theme.Dimens.Padding16
import com.michael.template.core.ui.theme.Dimens.Padding40
import com.michael.template.core.ui.theme.Dimens.size50
import com.michael.template.core.ui.theme.Dimens.textSize24
import com.michael.template.navigation.Destination
import kotlin.math.roundToInt

@Composable
private fun MenuComponent(
    modifier: Modifier,
    destinationClicked: (Destination) -> Unit,
    destinations: ImmutableList<Destination>,
) {
    Column(modifier = modifier.padding(Padding16), verticalArrangement = Arrangement.Center) {
        Spacer(vertical = Padding40)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile_image),
                contentDescription = "profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size50)
                    .clip(shape = CircleShape),
            )

            Text(
                text = "Michael",
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                color = Color.White,
                fontSize = textSize24,
                modifier = Modifier.padding(start = Padding16),
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        LazyColumn {
            items(destinations) { menuItem ->
                MenuItem(
                    icon = menuItem.icon,
                    title = menuItem.title,
                    menuAction = { destinationClicked(menuItem) },
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // settings
        MenuItem(
            icon = Icons.Filled.Settings,
            title = "Settings",
        ) { destinationClicked(Destination.Setting) }

        // logout
        MenuItem(
            icon = Icons.Filled.Close,
            title = "Logout",
        ) { destinationClicked(Destination.PasswordGenerator) }
    }
}

@Composable
fun MenuComponentWrapper(
    menuOffset: State<Offset>,
    alphaMenu: Float,
    destinations: ImmutableList<Destination>,
    destinationClicked: (Destination) -> Unit,
) {
    MenuComponent(
        Modifier
            .offset {
                IntOffset(
                    menuOffset.value.x.roundToInt(),
                    menuOffset.value.y.roundToInt(),
                )
            }
            .alpha(alphaMenu),
        destinationClicked = destinationClicked,
        destinations,
    )
}
