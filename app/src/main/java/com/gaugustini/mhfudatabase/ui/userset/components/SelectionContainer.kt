package com.gaugustini.mhfudatabase.ui.userset.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.ui.theme.Dimension

@Composable
fun SelectionContainer(
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimension.Size.large)
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                indication = null,
                interactionSource = null,
            ) { onSelected() }
    ) {
        content()
    }
}
