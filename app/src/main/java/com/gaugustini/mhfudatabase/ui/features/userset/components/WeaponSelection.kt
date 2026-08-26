package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.filter.WeaponFilter
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUIcons
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponSelection(
    weapons: List<Weapon>,
    filter: WeaponFilter = WeaponFilter(),
    onWeaponClick: (weaponId: Int) -> Unit = {},
    onFilterChange: (filter: WeaponFilter) -> Unit = {},
    onBack: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(true)

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            SelectionTopBar(
                scrollBehavior = scrollBehavior,
                navigateBack = onBack,
                onFilterClick = { showFilterSheet = true },
                onQueryChange = { onFilterChange(filter.copy(name = it)) },
                onDismiss = { onFilterChange(filter.copy(name = null)) },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Dimension.Padding.medium),
            userScrollEnabled = weapons.isNotEmpty(),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsWithDivider(
                items = weapons,
                key = { it.id }
            ) { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onWeaponClick = onWeaponClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }

        if (showFilterSheet) {
            WeaponFilterSheet(
                sheetState = filterSheetState,
                filter = filter,
                onFilterChange = onFilterChange,
                onDismiss = { showFilterSheet = false },
            )
        }
    }
}

@Composable
fun WeaponFilterSheet(
    sheetState: SheetState,
    filter: WeaponFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: WeaponFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val hunterType = filter.hunterType ?: HunterType.BOTH
    var weaponTypes = filter.weaponType ?: emptyList()
    var elementTypes = filter.elementType ?: emptyList()
    var rarities = filter.rarity ?: emptyList()
    var numberOfSlots = filter.numberOfSlots ?: emptyList()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimension.Padding.large)
        ) {
            Text(
                text = stringResource(R.string.user_set_filter_weapon_type),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                WeaponType.forHunterType(hunterType).forEach { weaponType ->
                    SelectionContainer(
                        selected = weaponType in weaponTypes,
                        onSelected = {
                            weaponTypes = if (weaponType in weaponTypes) {
                                weaponTypes - weaponType
                            } else {
                                weaponTypes + weaponType
                            }
                            onFilterChange(filter.copy(weaponType = weaponTypes.ifEmpty { null }))
                        },
                        modifier = Modifier.size(Dimension.Size.large)
                    ) {
                        Image(
                            painter = painterResource(
                                id = MHFUIcons.weapons[weaponType] ?: R.drawable.ic_ui_unknown
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.75f)
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.user_set_filter_element_type),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                WeaponElement.entries.forEach { elementType ->
                    SelectionContainer(
                        selected = elementType in elementTypes,
                        onSelected = {
                            elementTypes = if (elementType in elementTypes) {
                                elementTypes - elementType
                            } else {
                                elementTypes + elementType
                            }
                            onFilterChange(filter.copy(elementType = elementTypes.ifEmpty { null }))
                        },
                        modifier = Modifier.size(Dimension.Size.large)
                    ) {
                        Image(
                            painter = painterResource(
                                id = MHFUIcons.elements[elementType] ?: R.drawable.ic_ui_unknown
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.75f)
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.user_set_filter_rarity),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                repeat(10) {
                    SelectionContainer(
                        selected = (it + 1) in rarities,
                        onSelected = {
                            rarities = if ((it + 1) in rarities) {
                                rarities - (it + 1)
                            } else {
                                rarities + (it + 1)
                            }
                            onFilterChange(filter.copy(rarity = rarities.ifEmpty { null }))
                        }
                    ) {
                        Text(
                            text = (it + 1).toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.user_set_filter_number_of_slots),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                repeat(4) {
                    SelectionContainer(
                        selected = it in numberOfSlots,
                        onSelected = {
                            numberOfSlots = if (it in numberOfSlots) {
                                numberOfSlots - it
                            } else {
                                numberOfSlots + it
                            }
                            onFilterChange(filter.copy(numberOfSlots = numberOfSlots.ifEmpty { null }))
                        }
                    ) {
                        Text(
                            text = (it).toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun WeaponSelectionPreview() {
    Theme {
        WeaponSelection(
            weapons = PreviewWeaponData.weaponList,
        )
    }
}
