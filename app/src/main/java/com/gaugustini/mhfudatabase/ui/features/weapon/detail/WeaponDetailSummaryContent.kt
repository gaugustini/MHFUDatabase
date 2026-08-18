package com.gaugustini.mhfudatabase.ui.features.weapon.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ButtonPage
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
    scrollState: ScrollState = rememberScrollState(),
    onChangePage: (WeaponDetailPage) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    var statsExpanded by rememberSaveable { mutableStateOf(true) }
    var ammoExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeCreateAExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeCreateBExpanded by rememberSaveable { mutableStateOf(true) }
    var recipeUpgradeExpanded by rememberSaveable { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimension.Padding.medium),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        )

        if (weapon.type !in listOf(WeaponType.LIGHT_BOWGUN, WeaponType.HEAVY_BOWGUN)) {
            ButtonPage(
                title = stringResource(R.string.weapon_paths),
                onButtonClick = { onChangePage(WeaponDetailPage.PATHS) },
                modifier = Modifier.padding(horizontal = Dimension.Padding.medium)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.Padding.medium)
                .clip(RoundedCornerShape(Dimension.Radius.medium))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SectionHeader(
                title = stringResource(R.string.list_equipment_stats),
                isExpandable = true,
                expanded = statsExpanded,
                modifier = Modifier.clickable { statsExpanded = !statsExpanded }
            )
            AnimatedVisibility(
                visible = statsExpanded,
            ) {
                WeaponSummary(
                    weapon = weapon,
                )
            }
        }

        weapon.ammoBow?.let { ammoBow ->
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SectionHeader(
                    title = stringResource(R.string.weapon_bow_coating),
                    isExpandable = true,
                    expanded = ammoExpanded,
                    modifier = Modifier.clickable { ammoExpanded = !ammoExpanded }
                )
                AnimatedVisibility(
                    visible = ammoExpanded,
                ) {
                    WeaponAmmoBowSummary(
                        ammo = ammoBow,
                    )
                }
            }
        }

        weapon.ammoBowgun?.let { ammoBowgun ->
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.Padding.medium)
                    .clip(RoundedCornerShape(Dimension.Radius.medium))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                SectionHeader(
                    title = stringResource(R.string.weapon_ammo_bowgun),
                    isExpandable = true,
                    expanded = ammoExpanded,
                    modifier = Modifier.clickable { ammoExpanded = !ammoExpanded }
                )
                AnimatedVisibility(
                    visible = ammoExpanded,
                ) {
                    WeaponAmmoBowgunSummary(
                        ammo = ammoBowgun,
                    )
                }
            }
        }

        weapon.recipesCreate?.let { recipes ->
            if (recipes.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(
                            if (recipes.size == 1) R.string.list_recipe_create
                            else R.string.list_recipe_create_a
                        ),
                        isExpandable = true,
                        expanded = recipeCreateAExpanded,
                        modifier = Modifier.clickable { recipeCreateAExpanded = !recipeCreateAExpanded }
                    )
                    AnimatedVisibility(
                        visible = recipeCreateAExpanded,
                    ) {
                        Column {
                            recipes[0].ForEachWithDivider { item ->
                                ItemQuantityListItem(
                                    item = item,
                                    onItemClick = onItemClick,
                                )
                            }
                        }
                    }
                }

                if (recipes.size > 1) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = Dimension.Padding.medium)
                            .clip(RoundedCornerShape(Dimension.Radius.medium))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        SectionHeader(
                            title = stringResource(R.string.list_recipe_create_b),
                            isExpandable = true,
                            expanded = recipeCreateBExpanded,
                            modifier = Modifier.clickable {
                                recipeCreateBExpanded = !recipeCreateBExpanded
                            }
                        )
                        AnimatedVisibility(
                            visible = recipeCreateBExpanded,
                        ) {
                            Column {
                                recipes[1].ForEachWithDivider { item ->
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
        }

        weapon.recipeUpgrade?.let { recipe ->
            if (recipe.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimension.Padding.medium)
                        .clip(RoundedCornerShape(Dimension.Radius.medium))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SectionHeader(
                        title = stringResource(R.string.list_recipe_upgrade),
                        isExpandable = true,
                        expanded = recipeUpgradeExpanded,
                        modifier = Modifier.clickable { recipeUpgradeExpanded = !recipeUpgradeExpanded }
                    )
                    AnimatedVisibility(
                        visible = recipeUpgradeExpanded,
                    ) {
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
