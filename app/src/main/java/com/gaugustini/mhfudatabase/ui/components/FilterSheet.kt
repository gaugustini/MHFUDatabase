package com.gaugustini.mhfudatabase.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.ui.theme.Dimension

@Composable
fun <T> FilterSheet(
    title: String,
    items: List<T>,
    selectedItems: List<T>?,
    onItemsSelected: (List<T>) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    itemContent: @Composable (item: T, isSelected: Boolean, onClick: () -> Unit) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimension.Padding.large,
                    end = Dimension.Padding.large,
                    bottom = Dimension.Padding.large,
                )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    val currentSelected = selectedItems ?: emptyList()
                    val isSelected = item in currentSelected

                    itemContent(
                        item,
                        isSelected,
                    ) {
                        val updatedSelection = if (isSelected) {
                            currentSelected - item
                        } else {
                            currentSelected + item
                        }
                        onItemsSelected(updatedSelection)
                    }
                }
            }
        }
    }
}

@Composable
fun <T> FilterSheet(
    title: String,
    items: List<T>,
    selectedItems: List<T>?,
    onItemsSelected: (List<T>) -> Unit,
    labelProvider: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    FilterSheet(
        title = title,
        items = items,
        selectedItems = selectedItems,
        onItemsSelected = onItemsSelected,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        itemContent = { item, isSelected, onClick ->
            FilterChip(
                selected = isSelected,
                onClick = onClick,
                label = {
                    Text(
                        text = labelProvider(item),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    )
}
