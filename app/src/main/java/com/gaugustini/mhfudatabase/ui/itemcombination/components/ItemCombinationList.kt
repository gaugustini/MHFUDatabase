package com.gaugustini.mhfudatabase.ui.itemcombination.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemCombinationList(
    itemCombinations: List<ItemCombination>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(itemCombinations) { item ->
            ItemCombinationListItem(
                itemCombination = item,
                onItemClick = onItemClick,
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun ItemCombinationListItem(
    itemCombination: ItemCombination,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ItemIcon(
                type = itemCombination.itemCreatedIconType,
                color = itemCombination.itemCreatedIconColor,
                modifier = Modifier.size(Dimension.Size.large)
            )
        },
        headlineContent = {
            Text(
                text = itemCombination.itemCreatedName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Row {
                Text(
                    text = "${itemCombination.percentage}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(Dimension.Spacing.large))
                Text(
                    text = if (itemCombination.quantityMin == itemCombination.quantityMax) {
                        "x ${itemCombination.quantityMin}"
                    } else {
                        "x ${itemCombination.quantityMin}~${itemCombination.quantityMax}"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .requiredHeight(Dimension.Size.small)
                        .clickable { onItemClick(itemCombination.itemAId) }
                ) {
                    Text(
                        text = itemCombination.itemAName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    ItemIcon(
                        type = itemCombination.itemAIconType,
                        color = itemCombination.itemAIconColor,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .requiredHeight(Dimension.Size.small)
                        .clickable { onItemClick(itemCombination.itemBId) }
                ) {
                    Text(
                        text = itemCombination.itemBName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    ItemIcon(
                        type = itemCombination.itemBIconType,
                        color = itemCombination.itemBIconColor,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onItemClick(itemCombination.itemCreatedId) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemCombinationListPreview() {
    Theme {
        ItemCombinationList(PreviewItemData.itemCombinationList)
    }
}
