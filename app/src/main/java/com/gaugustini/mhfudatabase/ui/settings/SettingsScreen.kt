package com.gaugustini.mhfudatabase.ui.settings

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.ThemeMode
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.settings.components.SettingsDialogOptions
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun SettingsRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onThemeChange = viewModel::setThemeMode
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsState = SettingsState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onThemeChange: (ThemeMode) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_settings),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            ThemeSettingsItem(
                themeMode = uiState.themeMode,
                onThemeChange = onThemeChange
            )
        }
    }
}

@Composable
fun ThemeSettingsItem(
    themeMode: ThemeMode,
    modifier: Modifier = Modifier,
    onThemeChange: (ThemeMode) -> Unit,
) {
    val openOptions = remember { mutableStateOf(false) }
    val themeSystemString = stringResource(R.string.settings_theme_system)
    val themeLightString = stringResource(R.string.settings_theme_light)
    val themeDarkString = stringResource(R.string.settings_theme_dark)

    ListItemLayout(
        leadingContent = {
            Image(
                painter = painterResource(
                    if (LocalIsDarkTheme.current) R.drawable.ic_ui_theme_white else R.drawable.ic_ui_theme_black
                ),
                contentDescription = null,
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        },
        headlineContent = {
            Text(
                text = stringResource(R.string.settings_theme),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        supportingContent = {
            Text(
                text = when (themeMode) {
                    ThemeMode.SYSTEM -> themeSystemString
                    ThemeMode.LIGHT -> themeLightString
                    ThemeMode.DARK -> themeDarkString
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        modifier = modifier.clickable { openOptions.value = true },
    )

    if (openOptions.value) {
        SettingsDialogOptions(
            title = stringResource(R.string.settings_theme_options_title),
            options = listOf(themeSystemString, themeLightString, themeDarkString),
            selectedOption = when (themeMode) {
                ThemeMode.SYSTEM -> themeSystemString
                ThemeMode.LIGHT -> themeLightString
                ThemeMode.DARK -> themeDarkString
            },
            onConfirm = { option ->
                when (option) {
                    themeSystemString -> onThemeChange(ThemeMode.SYSTEM)
                    themeLightString -> onThemeChange(ThemeMode.LIGHT)
                    themeDarkString -> onThemeChange(ThemeMode.DARK)
                }
                openOptions.value = false
            },
            onDismiss = {
                openOptions.value = false
            },
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    Theme {
        SettingsScreen()
    }
}
