package com.gaugustini.mhfudatabase.ui.features.weapon.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemQuantityListItem
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponAmmoBowSummary
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponAmmoBowgunSummary
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponDetailSummaryContent(
    weapon: Weapon,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                WeaponIcon(
                    type = weapon.type,
                    rarity = weapon.rarity,
                )
            },
            title = weapon.name,
            subtitle = stringResource(R.string.weapon_rarity, weapon.rarity),
            description = weapon.description,
        )

        WeaponSummary(
            weapon = weapon,
        )

        weapon.ammoBow?.let { ammoBow ->
            WeaponAmmoBowSummary(
                ammo = ammoBow,
            )
        }

        weapon.ammoBowgun?.let { ammoBowgun ->
            WeaponAmmoBowgunSummary(
                ammo = ammoBowgun,
            )
        }

        weapon.recipeCreate?.let { recipe ->
            if (recipe.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_recipe_create),
                )
                Column {
                    recipe.ForEachWithDivider { item ->
                        ItemQuantityListItem(
                            item = item,
                            onItemClick = onItemClick,
                        )
                    }
                }
            }
        }

        weapon.recipeUpgrade?.let { recipe ->
            if (recipe.isNotEmpty()) {
                SectionHeader(
                    title = stringResource(R.string.list_recipe_upgrade),
                )
                Column {
                    recipe.ForEachWithDivider { item ->
                        ItemQuantityListItem(
                            item = item,
                            onItemClick = onItemClick,
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun WeaponDetailSummaryContentPreview() {
    Theme {
        WeaponDetailSummaryContent(
            weapon = PreviewWeaponData.weaponGS,
        )
    }
}
