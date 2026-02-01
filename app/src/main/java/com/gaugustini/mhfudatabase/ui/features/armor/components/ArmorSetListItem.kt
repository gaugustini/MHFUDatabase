package com.gaugustini.mhfudatabase.ui.features.armor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSetListItem(
    armorSet: ArmorSet,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onToggleExpand: () -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        ListItemLayout(
            leadingContent = {
                ArmorSetIcon(
                    rarity = armorSet.rarity,
                    modifier = Modifier.size(Dimension.Size.large)
                )
            },
            headlineContent = {
                Text(
                    text = armorSet.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
            backgroundColor = if (expanded) {
                MaterialTheme.colorScheme.surfaceContainerHighest
            } else {
                MaterialTheme.colorScheme.surface
            },
            modifier = Modifier.clickable { onToggleExpand() }
        )

        AnimatedVisibility(
            visible = expanded,
        ) {
            Column {
                armorSet.armors?.forEach { armor ->
                    ArmorListItem(
                        armor = armor,
                        onArmorClick = onArmorClick,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ArmorSetListItemPreview() {
    Theme {
        ArmorSetListItem(
            armorSet = PreviewArmorData.armorSet,
            expanded = true,
        )
    }
}
