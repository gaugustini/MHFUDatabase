package com.gaugustini.mhfudatabase.ui.features.item.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.filter.ItemFilter
import com.gaugustini.mhfudatabase.ui.components.FilterSheet
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.MHFUIcons
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: ItemListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ItemListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onFilterChange = viewModel::onFilterChange,
        onItemClick = onItemClick,
    )
}

@Composable
fun ItemListScreen(
    uiState: ItemListState = ItemListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onFilterChange: (filter: ItemFilter) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_item_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    ItemListFilter(
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
                items = uiState.items,
                key = { it.id }
            ) { item ->
                ItemListItem(
                    item = item,
                    onItemClick = onItemClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@Composable
fun ItemListFilter(
    filter: ItemFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: ItemFilter) -> Unit = {},
) {
    var showRarityFilterSheet by remember { mutableStateOf(false) }
    var showIconFilterSheet by remember { mutableStateOf(false) }
    var showColorFilterSheet by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        FilterChip(
            selected = !filter.rarity.isNullOrEmpty(),
            onClick = { showRarityFilterSheet = true },
            label = {
                Text(text = stringResource(R.string.item_filter_rarity))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )

        FilterChip(
            selected = !filter.icons.isNullOrEmpty(),
            onClick = { showIconFilterSheet = true },
            label = {
                Text(text = stringResource(R.string.item_filter_icon))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )

        FilterChip(
            selected = !filter.colors.isNullOrEmpty(),
            onClick = { showColorFilterSheet = true },
            label = {
                Text(text = stringResource(R.string.item_filter_color))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )
    }

    if (showRarityFilterSheet) {
        FilterSheet(
            title = stringResource(R.string.item_filter_rarity),
            items = (1..8).toList(),
            selectedItems = filter.rarity,
            onItemsSelected = { updatedRarities ->
                onFilterChange(filter.copy(rarity = updatedRarities))
            },
            labelProvider = { rarity -> rarity.toString() },
            onDismissRequest = { showRarityFilterSheet = false }
        )
    }

    if (showIconFilterSheet) {
        FilterSheet(
            title = stringResource(R.string.item_filter_icon),
            items = ItemIconType.entries,
            selectedItems = filter.icons,
            onItemsSelected = { updatedIcons ->
                onFilterChange(filter.copy(icons = updatedIcons))
            },
            onDismissRequest = { showIconFilterSheet = false },
            itemContent = { icon, isSelected, onClick ->
                FilterChip(
                    selected = isSelected,
                    onClick = onClick,
                    label = {
                        Image(
                            painter = painterResource(MHFUIcons.items[icon] ?: R.drawable.ic_ui_unknown),
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                )
            }
        )
    }

    if (showColorFilterSheet) {
        FilterSheet(
            title = stringResource(R.string.item_filter_color),
            items = ItemIconColor.entries,
            selectedItems = filter.colors,
            onItemsSelected = { updatedColors ->
                onFilterChange(filter.copy(colors = updatedColors))
            },
            onDismissRequest = { showColorFilterSheet = false },
            itemContent = { color, isSelected, onClick ->
                FilterChip(
                    selected = isSelected,
                    onClick = onClick,
                    label = {
                        Box(
                            modifier = Modifier
                                .size(Dimension.Size.extraSmall)
                                .background(
                                    color = MHFUColors.getItemColor(color),
                                    shape = CircleShape
                                )
                        )
                    }
                )
            }
        )
    }
}

@DevicePreviews
@Composable
fun ItemListScreenPreview(
    @PreviewParameter(ItemListScreenPreviewParamProvider::class) uiState: ItemListState
) {
    Theme {
        ItemListScreen(uiState)
    }
}

private class ItemListScreenPreviewParamProvider : PreviewParameterProvider<ItemListState> {

    override val values: Sequence<ItemListState> = sequenceOf(
        ItemListState(
            items = PreviewItemData.itemList,
        ),
    )

}
