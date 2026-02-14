package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.filter.WeaponFilter
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUIcons
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponSelection(
    weapons: List<Weapon>,
    filter: WeaponFilter = WeaponFilter(),
    onWeaponClick: (weaponId: Int) -> Unit = {},
    onFilterChange: (filter: WeaponFilter) -> Unit = {},
    onBack: () -> Unit = {},
) {
    var showSearchTextField by rememberSaveable { mutableStateOf(false) }
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(true)

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearchTextField) {
                        SearchTextField(
                            onQueryChange = {
                                onFilterChange(filter.copy(name = it))
                            },
                            onDismiss = {
                                showSearchTextField = false
                                onFilterChange(filter.copy(name = ""))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.user_set_weapon_selection),
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showFilterSheet = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                    if (!showSearchTextField) {
                        IconButton(
                            onClick = { showSearchTextField = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(weapons) { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onWeaponClick = onWeaponClick,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponFilterSheet(
    sheetState: SheetState,
    filter: WeaponFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: WeaponFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
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
                WeaponType.entries.forEach { weaponType ->
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
