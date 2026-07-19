package com.gaugustini.mhfudatabase.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

/**
 * Iterates through a list and executes a [content] block for each item,
 * inserting a [spacer] between them. Last item will not have a spacer.
 */
@Composable
fun <T> List<T>.ForEachWithSpacer(
    spacer: @Composable () -> Unit = {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    },
    content: @Composable (T) -> Unit
) {
    forEachIndexed { index, item ->
        content(item)
        if (index < lastIndex) {
            spacer()
        }
    }
}

/**
 * Adds a list of items to the lazy layout with a custom spacer between them.
 * The spacer is automatically omitted after the last item.
 *
 * @param items The data list to display.
 * @param key A factory of stable and unique keys representing the item.
 * @param itemContent The content lambda for each item.
 * @param spacer The optional separator lambda to draw between items.
 */
inline fun <T> LazyListScope.itemsWithSpacer(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline spacer: @Composable () -> Unit = {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    },
    crossinline itemContent: @Composable (T) -> Unit,

    ) {
    itemsIndexed(
        items = items,
        key = key?.let { safeKey -> { _, item -> safeKey(item) } },
    ) { index, item ->
        itemContent(item)
        if (index < items.lastIndex) {
            spacer()
        }
    }
}
