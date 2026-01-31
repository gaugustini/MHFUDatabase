package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun WhatsNewDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.whats_new_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.whats_new_text),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.dialog_confirm),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

@DevicePreviews
@Composable
fun WhatsNewDialogPreview() {
    Theme {
        WhatsNewDialog()
    }
}
