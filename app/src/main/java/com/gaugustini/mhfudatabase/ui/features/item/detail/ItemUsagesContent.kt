package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.ItemUsages
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageArmorListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageDecorationListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageWeaponListItem
import com.gaugustini.mhfudatabase.ui.features.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemUsagesContent(
    usages: ItemUsages,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (usages.combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                )
            }
            itemsIndexed(usages.combinations) { index, combination ->
                ItemCombinationListItem(
                    itemCombination = combination,
                    onItemClick = onItemClick,
                )
                if (index != usages.combinations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (usages.armors.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_armor),
                )
            }
            itemsIndexed(usages.armors) { index, armor ->
                UsageArmorListItem(
                    usage = armor,
                    onArmorClick = onArmorClick,
                )
                if (index != usages.armors.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (usages.decorations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_decoration),
                )
            }
            itemsIndexed(usages.decorations) { index, decoration ->
                UsageDecorationListItem(
                    usage = decoration,
                    onDecorationClick = onDecorationClick,
                )
                if (index != usages.decorations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (usages.weapons.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_weapon),
                )
            }
            itemsIndexed(usages.weapons) { index, weapon ->
                UsageWeaponListItem(
                    usage = weapon,
                    onWeaponClick = onWeaponClick,
                )
                if (index != usages.weapons.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemUsagesContentPreview() {
    Theme {
        ItemUsagesContent(
            usages = PreviewItemData.itemUsages,
        )
    }
}
