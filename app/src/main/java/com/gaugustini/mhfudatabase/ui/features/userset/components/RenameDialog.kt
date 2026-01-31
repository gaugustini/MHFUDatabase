package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews

@Composable
fun RenameDialog(
    setName: String,
    modifier: Modifier = Modifier,
    onConfirm: (name: String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var newName by rememberSaveable { mutableStateOf(setName) }

    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.user_set_dialog_rename_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(newName) }) {
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

@DevicePreviews
@Composable
fun RenameDialogPreview() {
    Theme {
        RenameDialog(
            setName = "New Set",
        )
    }
}
