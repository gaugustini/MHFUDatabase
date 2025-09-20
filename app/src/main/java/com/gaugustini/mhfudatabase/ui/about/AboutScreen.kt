package com.gaugustini.mhfudatabase.ui.about

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun AboutRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
) {
    AboutScreen(
        navigateBack = navigateBack,
        openSearch = openSearch,
    )
}

@Composable
fun AboutScreen(
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_about),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        AboutContent(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenPreview() {
    Theme {
        AboutScreen()
    }
}
