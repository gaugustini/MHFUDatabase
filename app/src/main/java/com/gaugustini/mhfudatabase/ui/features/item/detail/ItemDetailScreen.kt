package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

enum class ItemDetailPage {
    ITEM_SUMMARY,
    ITEM_USAGES,
    ITEM_SOURCES;
}

@Composable
fun ItemDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onDecorationClick: (decorationId: Int) -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    onLocationClick: (locationId: Int) -> Unit,
    onMonsterClick: (monsterId: Int) -> Unit,
    onQuestClick: (questId: Int) -> Unit,
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ItemDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onChangePage = viewModel::onChangePage,
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
        onItemClick = onItemClick,
        onLocationClick = onLocationClick,
        onMonsterClick = onMonsterClick,
        onQuestClick = onQuestClick,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun ItemDetailScreen(
    uiState: ItemDetailState = ItemDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangePage: (ItemDetailPage) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.item?.name ?: stringResource(R.string.screen_item_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == ItemDetailPage.ITEM_SUMMARY)
                        navigateBack()
                    else
                        onChangePage(ItemDetailPage.ITEM_SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.item != null) {
            when (uiState.page) {
                ItemDetailPage.ITEM_SUMMARY -> {
                    ItemSummaryContent(
                        item = uiState.item,
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                ItemDetailPage.ITEM_USAGES -> {
                    uiState.item.usages?.let { usages ->
                        ItemUsagesContent(
                            usages = usages,
                            onArmorClick = onArmorClick,
                            onDecorationClick = onDecorationClick,
                            onItemClick = onItemClick,
                            onWeaponClick = onWeaponClick,
                            onChangePage = onChangePage,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }

                ItemDetailPage.ITEM_SOURCES -> {
                    uiState.item.sources?.let { sources ->
                        ItemSourcesContent(
                            sources = sources,
                            onItemClick = onItemClick,
                            onLocationClick = onLocationClick,
                            onMonsterClick = onMonsterClick,
                            onQuestClick = onQuestClick,
                            onChangePage = onChangePage,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemDetailScreenPreview(
    @PreviewParameter(ItemDetailScreenPreviewParamProvider::class) uiState: ItemDetailState
) {
    Theme {
        ItemDetailScreen(uiState)
    }
}

private class ItemDetailScreenPreviewParamProvider : PreviewParameterProvider<ItemDetailState> {

    override val values: Sequence<ItemDetailState> = sequenceOf(
        ItemDetailState(
            page = ItemDetailPage.ITEM_SUMMARY,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            page = ItemDetailPage.ITEM_USAGES,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            page = ItemDetailPage.ITEM_SOURCES,
            item = PreviewItemData.item,
        ),
    )

}
