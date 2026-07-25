package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import kotlin.math.roundToInt

enum class NavigationType {
    MENU,
    BACK;
}

@Composable
fun TopBar(
    title: String,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigation: () -> Unit = {},
    openSearch: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeable = measurables.first().measure(constraints.copy(minHeight = 0))
            val totalHeight = placeable.height

            scrollBehavior.state.heightOffsetLimit = -totalHeight.toFloat()

            val currentHeight =
                (totalHeight + scrollBehavior.state.heightOffset.roundToInt()).coerceAtLeast(0)

            layout(constraints.maxWidth, currentHeight) {
                placeable.place(0, scrollBehavior.state.heightOffset.roundToInt())
            }
        },
        content = {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                        subtitleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
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
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
                ) {
                    bottomContent()
                }
            }
        }
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
