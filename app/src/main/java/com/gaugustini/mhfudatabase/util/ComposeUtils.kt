package com.gaugustini.mhfudatabase.util

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
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
    crossinline itemContent: @Composable LazyItemScope.(T) -> Unit,
) {
    itemsIndexed(
        items = items,
        key = key?.let { safeKey -> { _, item -> safeKey(item) } },
    ) { index, item ->
        itemContent(item)
        if (index < items.lastIndex) {
            val itemKey = key?.invoke(item) ?: index
            key("div_$itemKey") {
                divider()
            }
        }
    }
}

/**
 * Adds a list of items to the lazy layout with a custom divider between them,
 * providing the index of each item to the content and key lambdas.
 * The divider is automatically omitted after the last item.
 *
 * @param items The data list to display.
 * @param key A factory of stable and unique keys representing the item based on its index and data.
 * @param divider The optional separator lambda to draw between items.
 * @param itemContent The content lambda for each item, providing its index.
 */
inline fun <T> LazyListScope.itemsIndexedWithDivider(
    items: List<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline divider: @Composable () -> Unit = { AppHDivider() },
    crossinline itemContent: @Composable LazyItemScope.(index: Int, T) -> Unit,
) {
    itemsIndexed(
        items = items,
        key = key,
    ) { index, item ->
        itemContent(index, item)
        if (index < items.lastIndex) {
            val itemKey = key?.invoke(index, item) ?: index
            key("div_$itemKey") {
                divider()
            }
        }
    }
}

/**
 * Applies an expand/collapse animation for expandable sections inside lazy layouts.
 */
fun Modifier.animateItemExpandCollapse(
    lazyItemScope: LazyItemScope
): Modifier = this.then(
    with(lazyItemScope) {
        Modifier.animateItem(
            fadeInSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            ),
            fadeOutSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            placementSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }
)
