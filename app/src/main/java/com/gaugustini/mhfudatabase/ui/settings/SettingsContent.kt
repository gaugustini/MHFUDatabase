package com.gaugustini.mhfudatabase.ui.settings

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.ThemeMode
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val themeMode = UserPreferences.getThemeMode(context).collectAsState(initial = ThemeMode.SYSTEM)

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        ThemeSettingsItem(
            themeMode = themeMode.value,
            onThemeChange = {
                coroutineScope.launch {
                    UserPreferences.setThemeMode(context, it)
                }
            }
        )
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

@Composable
fun SettingsDialogOptions(
    title: String,
    options: List<String>,
    selectedOption: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userOption = remember { mutableStateOf(selectedOption) }

    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = Dimension.Padding.medium)
                            .clickable { userOption.value = option }
                    ) {
                        RadioButton(
                            selected = option == userOption.value,
                            onClick = null,
                            modifier = Modifier.padding(end = Dimension.Padding.large)
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(userOption.value) },
            ) {
                Text(
                    text = stringResource(R.string.settings_option_confirm),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    text = stringResource(R.string.settings_option_cancel),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        onDismissRequest = onDismiss,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsContentPreview() {
    Theme {
        SettingsContent()
    }
}
