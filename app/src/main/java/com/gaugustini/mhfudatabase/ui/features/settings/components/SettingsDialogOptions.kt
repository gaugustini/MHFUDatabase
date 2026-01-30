package com.gaugustini.mhfudatabase.ui.features.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

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

@DevicePreviews
@Composable
fun SettingsDialogOptionsPreview() {
    Theme {
        SettingsDialogOptions(
            title = "Title",
            options = listOf("Option A", "Option B", "Option C"),
            selectedOption = "Option A",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
