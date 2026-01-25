package com.gaugustini.mhfudatabase.ui.item.detail

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.domain.model.Usage
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.item.components.UsageArmorListItem
import com.gaugustini.mhfudatabase.ui.item.components.UsageDecorationListItem
import com.gaugustini.mhfudatabase.ui.item.components.UsageWeaponListItem
import com.gaugustini.mhfudatabase.ui.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemUsagesContent(
    combinations: List<ItemCombination>,
    armors: List<Usage<Armor>>,
    decorations: List<Usage<Decoration>>,
    weapons: List<Usage<Weapon>>,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                )
            }
            itemsIndexed(combinations) { index, combination ->
                ItemCombinationListItem(
                    itemCombination = combination,
                    onItemClick = onItemClick,
                )
                if (index != combinations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (armors.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_armor),
                )
            }
            itemsIndexed(armors) { index, armor ->
                UsageArmorListItem(
                    usage = armor,
                    onArmorClick = onArmorClick,
                )
                if (index != armors.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (decorations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_decoration),
                )
            }
            itemsIndexed(decorations) { index, decoration ->
                UsageDecorationListItem(
                    usage = decoration,
                    onDecorationClick = onDecorationClick,
                )
                if (index != decorations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (weapons.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_weapon),
                )
            }
            itemsIndexed(weapons) { index, weapon ->
                UsageWeaponListItem(
                    usage = weapon,
                    onWeaponClick = onWeaponClick,
                )
                if (index != weapons.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemUsagesContentPreview() {
    Theme {
        ItemUsagesContent(
            combinations = PreviewItemData.itemCombinationList,
            armors = PreviewItemData.itemUsageArmorList,
            decorations = PreviewItemData.itemUsageDecorationList,
            weapons = PreviewItemData.itemUsageWeaponList,
        )
    }
}
