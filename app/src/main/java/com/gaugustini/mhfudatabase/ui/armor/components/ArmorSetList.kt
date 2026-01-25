package com.gaugustini.mhfudatabase.ui.armor.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorSetIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSetList(
    armorSets: List<ArmorSet>,
    expandedArmorSets: Set<Int>,
    modifier: Modifier = Modifier,
    onToggleExpand: (armorSetId: Int) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(armorSets) { armorSet ->
            ArmorSetListItem(
                armorSet = armorSet,
                armors = armorSet.armors ?: emptyList(),
                expanded = armorSet.id in expandedArmorSets,
                onToggleExpand = { onToggleExpand(armorSet.id) },
                onArmorClick = onArmorClick,
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun ArmorSetListItem(
    armorSet: ArmorSet,
    armors: List<Armor>,
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
            ArmorList(
                armors = armors,
                onArmorClick = onArmorClick,
                showDivider = false,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorSetListPreview() {
    Theme {
        ArmorSetList(
            armorSets = PreviewArmorData.armorSetList,
            expandedArmorSets = setOf(2),
        )
    }
}
