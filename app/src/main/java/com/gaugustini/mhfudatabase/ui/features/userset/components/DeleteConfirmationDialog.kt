package com.gaugustini.mhfudatabase.ui.features.userset.components

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.user_set_dialog_delete_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.user_set_dialog_confirm),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.user_set_dialog_cancel),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeleteConfirmationDialogPreview() {
    Theme {
        DeleteConfirmationDialog()
    }
}
