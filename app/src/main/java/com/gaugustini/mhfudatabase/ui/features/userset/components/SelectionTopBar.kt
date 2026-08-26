package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import kotlin.math.roundToInt

@Composable
fun SelectionTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    navigateBack: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
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
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                    )
            ) {
                SelectionInputText(
                    onBackClick = navigateBack,
                    onFilterClick = onFilterClick,
                    onQueryChange = onQueryChange,
                    onDismiss = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
fun SelectionInputText(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var query by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = {
            query = it
            onQueryChange(it)
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.user_set_selection_search),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            query = ""
                            focusManager.clearFocus()
                            onDismiss()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                }
                IconButton(
                    onClick = onFilterClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() },
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier.padding(Dimension.Padding.medium)
    )
}
