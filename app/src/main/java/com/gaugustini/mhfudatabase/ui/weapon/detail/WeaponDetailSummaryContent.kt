package com.gaugustini.mhfudatabase.ui.weapon.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.AmmoBow
import com.gaugustini.mhfudatabase.data.model.AmmoBowgun
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.item.components.ItemQuantityList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponAmmoBowSummary
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponAmmoBowgunSummary
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponSummary
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponDetailSummaryContent(
    weapon: Weapon,
    ammoBow: AmmoBow?,
    ammoBowgun: AmmoBowgun?,
    modifier: Modifier = Modifier,
    recipeCreate: List<ItemQuantity> = emptyList(),
    recipeUpgrade: List<ItemQuantity> = emptyList(),
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

        if (ammoBow != null) {
            WeaponAmmoBowSummary(
                ammo = ammoBow,
            )
        }

        if (ammoBowgun != null) {
            WeaponAmmoBowgunSummary(
                ammo = ammoBowgun,
            )
        }

        if (recipeCreate.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.list_recipe_create),
            )
            ItemQuantityList(
                items = recipeCreate,
                onItemClick = onItemClick,
            )
        }

        if (recipeUpgrade.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.list_recipe_upgrade),
            )
            ItemQuantityList(
                items = recipeUpgrade,
                onItemClick = onItemClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponDetailSummaryContentPreview() {
    Theme {
        WeaponDetailSummaryContent(
            weapon = PreviewWeaponData.weaponGS,
            ammoBow = PreviewWeaponData.ammoBow,
            ammoBowgun = PreviewWeaponData.ammoBowgun,
            recipeCreate = PreviewItemData.itemQuantityList,
            recipeUpgrade = PreviewItemData.itemQuantityList,
        )
    }
}
