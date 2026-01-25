package com.gaugustini.mhfudatabase.ui.armor.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ArmorIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotsIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorList(
    armors: List<Armor>,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        armors.forEachIndexed { index, armor ->
            ArmorListItem(
                armor = armor,
                onArmorClick = onArmorClick,
            )
            if (showDivider && index != armors.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ArmorListItem(
    armor: Armor,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ArmorIcon(
                type = armor.type,
                rarity = armor.rarity,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = armor.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Text(
                text = stringResource(R.string.armor_rarity, armor.rarity),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingContent = {
            SlotsIcon(
                numberOfSlots = armor.numberOfSlots,
                modifier = Modifier.size(
                    width = Dimension.Size.tiny * 3,
                    height = Dimension.Size.tiny
                )
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onArmorClick(armor.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorListPreview() {
    Theme {
        ArmorList(PreviewArmorData.armorList)
    }
}
