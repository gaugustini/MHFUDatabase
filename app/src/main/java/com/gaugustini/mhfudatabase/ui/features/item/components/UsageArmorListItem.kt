package com.gaugustini.mhfudatabase.ui.features.item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Usage
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun UsageArmorListItem(
    usage: Usage<Armor>,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ArmorIcon(
                type = usage.craftable.type,
                rarity = usage.craftable.rarity,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = usage.craftable.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            Text(
                text = usage.quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onArmorClick(usage.craftable.id) }
    )
}

@DevicePreviews
@Composable
fun UsageArmorListItemPreview() {
    Theme {
        UsageArmorListItem(
            usage = PreviewItemData.itemUsageArmor,
        )
    }
}
