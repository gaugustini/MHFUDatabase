package com.gaugustini.mhfudatabase.util

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable

/**
 * Iterates through a list and executes a [content] block for each item,
 * inserting a [divider] between them. Last item will not have a divider.
 */
@Composable
fun <T> List<T>.ForEachWithDivider(
    divider: @Composable () -> Unit = { HorizontalDivider() },
    content: @Composable (T) -> Unit
) {
    forEachIndexed { index, item ->
        content(item)
        if (index < lastIndex) {
            divider()
        }
    }
}
