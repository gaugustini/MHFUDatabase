package com.gaugustini.mhfudatabase.ui.features.weapon.detail

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
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponDetailPathsContent(
    paths: List<List<Weapon>>,
    upgrades: List<Weapon>,
    finals: List<Weapon>,
    modifier: Modifier = Modifier,
    onChangePage: (WeaponDetailPage) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    var pathsExpanded by rememberSaveable { mutableStateOf(paths.indices.toSet()) }
    var upgradesExpanded by rememberSaveable { mutableStateOf(true) }
    var finalsExpanded by rememberSaveable { mutableStateOf(true) }

    BackHandler {
        onChangePage(WeaponDetailPage.SUMMARY)
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        paths.forEachIndexed { pathIndex, path ->
            val isPathExpanded = pathIndex in pathsExpanded

            item {
                SectionHeader(
                    title = if (paths.size == 1) {
                        stringResource(R.string.weapon_path)
                    } else {
                        stringResource(R.string.weapon_path_details, pathIndex + 1)
                    },
                    isExpandable = true,
                    expanded = isPathExpanded,
                    modifier = Modifier
                        .padding(bottom = if (isPathExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (isPathExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (isPathExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable {
                            pathsExpanded = if (isPathExpanded) {
                                pathsExpanded - pathIndex
                            } else {
                                pathsExpanded + pathIndex
                            }
                        }
                )
            }

            if (isPathExpanded) {
                itemsIndexed(
                    items = path,
                    key = { _, weapon -> "path_${pathIndex}_${weapon.id}" }
                ) { index, weapon ->
                    val isLastWeapon = index == path.lastIndex
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        WeaponListItem(
                            weapon = weapon,
                            onWeaponClick = { onWeaponClick(weapon.id) },
                        )
                        if (!isLastWeapon) {
                            AppHDivider()
                        }
                    }
                    if (isLastWeapon) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (upgrades.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.weapon_upgrades),
                    isExpandable = true,
                    expanded = upgradesExpanded,
                    modifier = Modifier
                        .padding(bottom = if (upgradesExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (upgradesExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (upgradesExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { upgradesExpanded = !upgradesExpanded }
                )
            }

            if (upgradesExpanded) {
                itemsIndexed(
                    items = upgrades,
                    key = { _, weapon -> "upgrades_${weapon.id}" }
                ) { index, weapon ->
                    val isLastWeapon = index == upgrades.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        WeaponListItem(
                            weapon = weapon,
                            onWeaponClick = { onWeaponClick(weapon.id) },
                        )
                        if (!isLastWeapon) {
                            AppHDivider()
                        }
                    }
                    if (isLastWeapon) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = stringResource(R.string.weapon_finals),
                isExpandable = true,
                expanded = finalsExpanded,
                modifier = Modifier
                    .padding(bottom = if (finalsExpanded) 0.dp else Dimension.Padding.medium)
                    .clip(
                        RoundedCornerShape(
                            topStart = Dimension.Radius.medium,
                            topEnd = Dimension.Radius.medium,
                            bottomStart = if (finalsExpanded) 0.dp else Dimension.Radius.medium,
                            bottomEnd = if (finalsExpanded) 0.dp else Dimension.Radius.medium
                        )
                    )
                    .clickable { finalsExpanded = !finalsExpanded }
            )
        }

        if (finalsExpanded) {
            itemsIndexed(
                items = finals,
                key = { _, weapon -> "finals_${weapon.id}" }
            ) { index, weapon ->
                val isLastWeapon = index == finals.lastIndex

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                                bottomEnd = if (isLastWeapon) Dimension.Radius.medium else 0.dp,
                            )
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .animateItemExpandCollapse(this)
                ) {
                    WeaponListItem(
                        weapon = weapon,
                        onWeaponClick = { onWeaponClick(weapon.id) },
                    )
                    if (!isLastWeapon) {
                        AppHDivider()
                    }
                }
                if (isLastWeapon) {
                    Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun WeaponDetailPathsContentPreview() {
    Theme {
        WeaponDetailPathsContent(
            paths = listOf(PreviewWeaponData.weaponList),
            upgrades = PreviewWeaponData.weaponList,
            finals = PreviewWeaponData.weaponList,
        )
    }
}
