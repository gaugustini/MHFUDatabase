package com.gaugustini.mhfudatabase.ui.features.item.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.item.components.ItemListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
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
        onItemClick = onItemClick,
    )
}

@Composable
fun ItemListScreen(
    uiState: ItemListState = ItemListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_item_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.items) { item ->
                ItemListItem(
                    item = item,
                    onItemClick = onItemClick,
                )
                HorizontalDivider()
            }
        }
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
