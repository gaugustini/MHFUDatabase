package com.gaugustini.mhfudatabase.ui.features.itemcombination.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemCombinationListItem(
    combination: ItemCombination,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ItemIcon(
                type = combination.itemCreated.iconType,
                color = combination.itemCreated.iconColor,
                modifier = Modifier.size(Dimension.Size.large)
            )
        },
        headlineContent = {
            Text(
                text = combination.itemCreated.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Row {
                Text(
                    text = "${combination.percentage}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(Dimension.Spacing.large))
                Text(
                    text = if (combination.quantityMin == combination.quantityMax) {
                        "x ${combination.quantityMin}"
                    } else {
                        "x ${combination.quantityMin}~${combination.quantityMax}"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(Dimension.Spacing.large))
                if (combination.type == ItemCombinationType.TREASURE) {
                    Text(
                        text = stringResource(R.string.combination_treasure),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = MaterialTheme.shapes.small,
                            )
                            .padding(Dimension.Padding.small)
                    )
                }
                if (combination.type == ItemCombinationType.ALCHEMY) {
                    Text(
                        text = stringResource(R.string.combination_alchemy),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = MaterialTheme.shapes.small,
                            )
                            .padding(Dimension.Padding.small)
                    )
                }
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
                        .clickable { onItemClick(combination.itemA.id) }
                ) {
                    Text(
                        text = combination.itemA.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    ItemIcon(
                        type = combination.itemA.iconType,
                        color = combination.itemA.iconColor,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .requiredHeight(Dimension.Size.small)
                        .clickable { onItemClick(combination.itemB.id) }
                ) {
                    Text(
                        text = combination.itemB.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    ItemIcon(
                        type = combination.itemB.iconType,
                        color = combination.itemB.iconColor,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onItemClick(combination.itemCreated.id) }
    )
}

@DevicePreviews
@Composable
fun ItemCombinationListPreview() {
    Theme {
        ItemCombinationListItem(
            combination = PreviewItemData.itemCombination,
        )
    }
}
