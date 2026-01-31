package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

enum class NavigationType {
    MENU,
    BACK;
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
    navigation: () -> Unit = {},
    openSearch: () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigation,
            ) {
                Icon(
                    imageVector = when (navigationType) {
                        NavigationType.MENU -> Icons.Default.Menu
                        NavigationType.BACK -> Icons.AutoMirrored.Filled.ArrowBack
                    },
                    contentDescription = null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            }
        },
        actions = {
            actions()
            IconButton(
                onClick = openSearch,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            }
        },
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun TopBarDrawerPreview() {
    Theme {
        TopBar(
            title = "Database",
            navigationType = NavigationType.MENU,
        )
    }
}

@DevicePreviews
@Composable
fun TopBarBackPreview() {
    Theme {
        TopBar(
            title = "Database",
            navigationType = NavigationType.BACK,
        )
    }
}
