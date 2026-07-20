package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.ItemUsages
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageArmorListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageDecorationListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.UsageWeaponListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.VeggieUsageListItem
import com.gaugustini.mhfudatabase.ui.features.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemUsagesContent(
    usages: ItemUsages,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
    onChangePage: (ItemDetailPage) -> Unit = {},
) {
    var combinationsExpanded by rememberSaveable { mutableStateOf(true) }
    var veggieExpanded by rememberSaveable { mutableStateOf(true) }
    var armorExpanded by rememberSaveable { mutableStateOf(true) }
    var decorationExpanded by rememberSaveable { mutableStateOf(true) }
    var weaponExpanded by rememberSaveable { mutableStateOf(true) }

    BackHandler {
        onChangePage(ItemDetailPage.ITEM_SUMMARY)
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        if (usages.combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                    isExpandable = true,
                    expanded = combinationsExpanded,
                    modifier = Modifier
                        .padding(bottom = if (combinationsExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (combinationsExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (combinationsExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { combinationsExpanded = !combinationsExpanded }
                )
            }
            if (combinationsExpanded) {
                itemsIndexed(
                    items = usages.combinations,
                    key = { _, combination -> "comb_${combination.itemCreated.id}" }
                ) { index, combination ->
                    val isLastItem = index == usages.combinations.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        ItemCombinationListItem(
                            combination = combination,
                            onItemClick = onItemClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (usages.veggieTrades.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_trade_veggie),
                    isExpandable = true,
                    expanded = veggieExpanded,
                    modifier = Modifier
                        .padding(bottom = if (veggieExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (veggieExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (veggieExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { veggieExpanded = !veggieExpanded }
                )
            }
            if (veggieExpanded) {
                itemsIndexed(
                    items = usages.veggieTrades,
                    key = { _, trade -> "veg_${trade.location.id}_${trade.trade.itemTraded.id}" }
                ) { index, trade ->
                    val isLastItem = index == usages.veggieTrades.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        VeggieUsageListItem(
                            usage = trade,
                            onItemClick = onItemClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (usages.armors.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_armor),
                    isExpandable = true,
                    expanded = armorExpanded,
                    modifier = Modifier
                        .padding(bottom = if (armorExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (armorExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (armorExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { armorExpanded = !armorExpanded }
                )
            }
            if (armorExpanded) {
                itemsIndexed(
                    items = usages.armors,
                    key = { _, armor -> "armor_${armor.craftable.id}_${armor.quantity}" }
                ) { index, armor ->
                    val isLastItem = index == usages.armors.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        UsageArmorListItem(
                            usage = armor,
                            onArmorClick = onArmorClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (usages.decorations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_decoration),
                    isExpandable = true,
                    expanded = decorationExpanded,
                    modifier = Modifier
                        .padding(bottom = if (decorationExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (decorationExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (decorationExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { decorationExpanded = !decorationExpanded }
                )
            }
            if (decorationExpanded) {
                itemsIndexed(
                    items = usages.decorations,
                    key = { _, decoration -> "dec_${decoration.craftable.id}_${decoration.quantity}" }
                ) { index, decoration ->
                    val isLastItem = index == usages.decorations.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        UsageDecorationListItem(
                            usage = decoration,
                            onDecorationClick = onDecorationClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (usages.weapons.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_weapon),
                    isExpandable = true,
                    expanded = weaponExpanded,
                    modifier = Modifier
                        .padding(bottom = if (weaponExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (weaponExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (weaponExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { weaponExpanded = !weaponExpanded }
                )
            }
            if (weaponExpanded) {
                itemsIndexed(
                    items = usages.weapons,
                    key = { _, weapon -> "weapon_${weapon.craftable.id}_${weapon.quantity}" } // TODO: resolve conflicted keys, eq. `Hunter's Bow III`
                ) { index, weapon ->
                    val isLastItem = index == usages.weapons.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        UsageWeaponListItem(
                            usage = weapon,
                            onWeaponClick = onWeaponClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
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
