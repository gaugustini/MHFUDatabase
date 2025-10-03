package com.gaugustini.mhfudatabase.ui.item.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.ui.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun ItemUsagesContent(
    combinations: List<ItemCombination>,
    armors: List<Armor>,
    weapons: List<Weapon>,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        if (combinations.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_crafting),
            )

            combinations.forEach { combination ->
                ItemCombinationListItem(
                    itemCombination = combination,
                    onItemClick = onItemClick,
                )
                HorizontalDivider()
            }
        }

        if (armors.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_armor),
            )

            armors.forEach { armor ->
                ArmorListItem(
                    armor = armor,
                    onArmorClick = onArmorClick,
                )
                HorizontalDivider()
            }
        }

        if (weapons.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_weapon),
            )

            weapons.forEach { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onWeaponClick = onWeaponClick,
                )
                HorizontalDivider()
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
            armors = PreviewArmorData.armorList,
            weapons = PreviewWeaponData.weaponList,
        )
    }
}
