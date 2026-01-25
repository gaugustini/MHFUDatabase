package com.gaugustini.mhfudatabase.ui.features.itemcombination.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.itemcombination.components.ItemCombinationList
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemCombinationListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: ItemCombinationListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ItemCombinationListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onItemClick = onItemClick,
    )
}

@Composable
fun ItemCombinationListScreen(
    uiState: ItemCombinationListState = ItemCombinationListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_item_combination_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        ItemCombinationList(
            itemCombinations = uiState.itemCombinations,
            onItemClick = onItemClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemCombinationListScreenPreview(
    @PreviewParameter(ItemCombinationListScreenPreviewParmProvider::class) uiState: ItemCombinationListState
) {
    Theme {
        ItemCombinationListScreen(uiState)
    }
}

private class ItemCombinationListScreenPreviewParmProvider :
    PreviewParameterProvider<ItemCombinationListState> {

    override val values: Sequence<ItemCombinationListState> = sequenceOf(
        ItemCombinationListState(
            itemCombinations = PreviewItemData.itemCombinationList
        ),
    )

}
