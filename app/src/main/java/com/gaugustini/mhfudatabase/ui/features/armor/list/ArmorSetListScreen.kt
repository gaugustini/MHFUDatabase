package com.gaugustini.mhfudatabase.ui.features.armor.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.filter.ArmorSetFilter
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.armor.components.ArmorSetListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorSetListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onArmorSetClick: (armorSetId: Int) -> Unit,
    viewModel: ArmorSetListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArmorSetListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onToggleExpand = viewModel::onToggleExpansion,
        onFilterChange = viewModel::onFilterChange,
        onArmorClick = onArmorClick,
        onArmorSetClick = onArmorSetClick,
    )
}

@Composable
fun ArmorSetListScreen(
    uiState: ArmorSetListState = ArmorSetListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onToggleExpand: (armorSetId: Int) -> Unit = {},
    onFilterChange: (filter: ArmorSetFilter) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onArmorSetClick: (armorSetId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_armor_set_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    ArmorSetListFilter(
                        filter = uiState.filter,
                        onFilterChange = onFilterChange,
                    )
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Dimension.Padding.medium),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsWithDivider(
                items = uiState.armorSets,
                key = { it.id }
            ) { armorSet ->
                ArmorSetListItem(
                    armorSet = armorSet,
                    expanded = armorSet.id in uiState.expandedArmorSets,
                    onToggleExpand = { onToggleExpand(armorSet.id) },
                    onArmorClick = onArmorClick,
                    onArmorSetClick = onArmorSetClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@Composable
fun ArmorSetListFilter(
    filter: ArmorSetFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: ArmorSetFilter) -> Unit = {},
) {
    var typeMenuExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        Box {
            FilterChip(
                selected = filter.hunterType != null && filter.hunterType != HunterType.BOTH,
                onClick = { typeMenuExpanded = true },
                label = {
                    Text(
                        text = stringResource(
                            when (filter.hunterType) {
                                HunterType.BLADE -> R.string.armor_set_filter_hunter_blade
                                HunterType.GUNNER -> R.string.armor_set_filter_hunter_gunner
                                else -> R.string.armor_set_filter_hunter_all
                            }
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                },
            )

            DropdownMenu(
                expanded = typeMenuExpanded,
                onDismissRequest = { typeMenuExpanded = false }
            ) {
                HunterType.entries.forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(
                                    when (type) {
                                        HunterType.BOTH -> R.string.armor_set_filter_hunter_all
                                        HunterType.BLADE -> R.string.armor_set_filter_hunter_blade
                                        HunterType.GUNNER -> R.string.armor_set_filter_hunter_gunner
                                    }
                                )
                            )
                        },
                        onClick = {
                            onFilterChange(
                                filter.copy(hunterType = if (type == HunterType.BOTH) null else type)
                            )
                            typeMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ArmorSetListScreenPreview(
    @PreviewParameter(ArmorSetListScreenPreviewParamProvider::class) uiState: ArmorSetListState
) {
    Theme {
        ArmorSetListScreen(uiState)
    }
}

private class ArmorSetListScreenPreviewParamProvider : PreviewParameterProvider<ArmorSetListState> {

    override val values: Sequence<ArmorSetListState> = sequenceOf(
        ArmorSetListState(
            armorSets = PreviewArmorData.armorSetList,
            expandedArmorSets = setOf(1),
        ),
    )

}
