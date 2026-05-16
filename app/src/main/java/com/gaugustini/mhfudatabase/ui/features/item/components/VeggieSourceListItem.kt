package com.gaugustini.mhfudatabase.ui.features.item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.domain.model.VeggieSource
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun VeggieSourceListItem(
    source: VeggieSource,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.large),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(source.trade.itemTraded.id) }
            .padding(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            ItemIcon(
                type = source.trade.itemTraded.iconType,
                color = source.trade.itemTraded.iconColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
            Column {
                Text(
                    text = source.trade.itemTraded.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = source.location.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .defaultMinSize(minHeight = Dimension.Size.small)
                    .clickable { onItemClick(source.trade.itemCommon.id) }
            ) {
                ItemIcon(
                    type = source.trade.itemCommon.iconType,
                    color = source.trade.itemCommon.iconColor,
                    modifier = Modifier.size(Dimension.Size.small)
                )
                Text(
                    text = if (source.trade.itemCommon.id == source.trade.itemRare.id) {
                        "${source.trade.itemCommon.name}\n(100%)"
                    } else {
                        "${source.trade.itemCommon.name}\n(80%)"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (source.trade.itemCommon.id != source.trade.itemRare.id) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .defaultMinSize(minHeight = Dimension.Size.small)
                        .clickable { onItemClick(source.trade.itemRare.id) }
                ) {
                    ItemIcon(
                        type = source.trade.itemRare.iconType,
                        color = source.trade.itemRare.iconColor,
                        modifier = Modifier.size(Dimension.Size.small)
                    )
                    Text(
                        text = "${source.trade.itemRare.name}\n(20%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun VeggieSourceListItemPreview() {
    Theme {
        VeggieSourceListItem(
            source = PreviewItemData.veggieSource,
        )
    }
}
