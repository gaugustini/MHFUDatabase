package com.gaugustini.mhfudatabase.ui.features.userset.components

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

data class ArmorSelectionFilter(
    val name: String = "",
    val armorType: EquipmentType = EquipmentType.ARMOR_HEAD,
    val numberOfSlots: List<Int> = emptyList(),
    val rarity: List<Int> = emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmorSelection(
    armors: List<Armor>,
    filter: ArmorSelectionFilter = ArmorSelectionFilter(),
    onArmorClick: (armorId: Int) -> Unit = {},
    onFilterChange: (filter: ArmorSelectionFilter) -> Unit = {},
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
                            text = stringResource(id = R.string.user_set_armor_selection),
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
            items(armors) { armor ->
                ArmorListItem(
                    armor = armor,
                    onArmorClick = onArmorClick
                )
            }
        }
    }

    if (showFilterSheet) {
        ArmorFilterSheet(
            sheetState = filterSheetState,
            filter = filter,
            onFilterChange = onFilterChange,
            onDismiss = { showFilterSheet = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmorFilterSheet(
    sheetState: SheetState,
    filter: ArmorSelectionFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: ArmorSelectionFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var newFilter = filter

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
                        selected = (it + 1) in newFilter.rarity,
                        onSelected = {
                            newFilter = if ((it + 1) in newFilter.rarity) {
                                newFilter.copy(rarity = newFilter.rarity - (it + 1))
                            } else {
                                newFilter.copy(rarity = newFilter.rarity + (it + 1))
                            }
                            onFilterChange(newFilter)
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
                        selected = it in newFilter.numberOfSlots,
                        onSelected = {
                            newFilter = if (it in newFilter.numberOfSlots) {
                                newFilter.copy(numberOfSlots = newFilter.numberOfSlots - it)
                            } else {
                                newFilter.copy(numberOfSlots = newFilter.numberOfSlots + it)
                            }
                            onFilterChange(newFilter)
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArmorSelectionPreview(
) {
    Theme {
        ArmorSelection(
            armors = PreviewArmorData.armorList,
        )
    }
}
