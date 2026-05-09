package com.gaugustini.mhfudatabase.ui.features.veggie.components

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
import com.gaugustini.mhfudatabase.domain.model.VeggieTrade
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewVeggieData

@Composable
fun VeggieTradeListItem(
    veggieTrade: VeggieTrade,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.large),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(veggieTrade.itemTraded.id) }
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
                type = veggieTrade.itemTraded.iconType,
                color = veggieTrade.itemTraded.iconColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
            Text(
                text = veggieTrade.itemTraded.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
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
                    .clickable { onItemClick(veggieTrade.itemCommon.id) }
            ) {
                ItemIcon(
                    type = veggieTrade.itemCommon.iconType,
                    color = veggieTrade.itemCommon.iconColor,
                    modifier = Modifier.size(Dimension.Size.small)
                )
                Text(
                    text = if (veggieTrade.itemCommon.id == veggieTrade.itemRare.id) {
                        "${veggieTrade.itemCommon.name}\n(100%)"
                    } else {
                        "${veggieTrade.itemCommon.name}\n(80%)"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (veggieTrade.itemCommon.id != veggieTrade.itemRare.id) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .defaultMinSize(minHeight = Dimension.Size.small)
                        .clickable { onItemClick(veggieTrade.itemRare.id) }
                ) {
                    ItemIcon(
                        type = veggieTrade.itemRare.iconType,
                        color = veggieTrade.itemRare.iconColor,
                        modifier = Modifier.size(Dimension.Size.small)
                    )
                    Text(
                        text = "${veggieTrade.itemRare.name}\n(20%)",
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
fun VeggieTradeListItemPreview() {
    Theme {
        VeggieTradeListItem(
            veggieTrade = PreviewVeggieData.veggieTrade,
        )
    }
}
