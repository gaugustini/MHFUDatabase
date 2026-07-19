package com.gaugustini.mhfudatabase.util

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.gaugustini.mhfudatabase.ui.components.AppHDivider

/**
 * Iterates through a list and executes a [content] block for each item,
 * inserting a [divider] between them. Last item will not have a divider.
 */
@Composable
fun <T> List<T>.ForEachWithDivider(
    divider: @Composable () -> Unit = { AppHDivider() },
    content: @Composable (T) -> Unit
) {
    forEachIndexed { index, item ->
        content(item)
        if (index < lastIndex) {
            divider()
        }
    }
}

/**
 * Adds a list of items to the lazy layout with a custom divider between them.
 * The divider is automatically omitted after the last item.
 *
 * @param items The data list to display.
 * @param key A factory of stable and unique keys representing the item.
 * @param divider The optional separator lambda to draw between items.
 * @param itemContent The content lambda for each item.
 */
inline fun <T> LazyListScope.itemsWithDivider(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline divider: @Composable () -> Unit = { AppHDivider() },
    crossinline itemContent: @Composable (T) -> Unit,
) {
    itemsIndexed(
        items = items,
        key = key?.let { safeKey -> { _, item -> safeKey(item) } },
    ) { index, item ->
        itemContent(item)
        if (index < items.lastIndex) {
            divider()
        }
    }
}
