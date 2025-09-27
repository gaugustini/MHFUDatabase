package com.gaugustini.mhfudatabase.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme

enum class NavigationType {
    MENU,
    BACK
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarDrawerPreview() {
    Theme {
        TopBar(
            title = "Database",
            navigationType = NavigationType.MENU,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarBackPreview() {
    Theme {
        TopBar(
            title = "Database",
            navigationType = NavigationType.BACK,
        )
    }
}
