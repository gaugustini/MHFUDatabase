package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.EmptyContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

enum class ItemDetailTab(@get:StringRes val title: Int) {
    ITEM_SUMMARY(R.string.tab_item_summary),
    ITEM_USAGES(R.string.tab_item_usages),
    ITEM_SOURCES(R.string.tab_item_sources);
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
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ItemDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
        onItemClick = onItemClick,
        onLocationClick = onLocationClick,
        onMonsterClick = onMonsterClick,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun ItemDetailScreen(
    uiState: ItemDetailState = ItemDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { ItemDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = ItemDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.item?.name ?: stringResource(R.string.screen_item_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.item != null) {
            when (ItemDetailTab.entries[tabIndex]) {
                ItemDetailTab.ITEM_SUMMARY -> {
                    ItemSummaryContent(
                        item = uiState.item,
                    )
                }

                ItemDetailTab.ITEM_USAGES -> {
                    uiState.item.usages?.let { usages ->
                        if (usages.isEmpty()) {
                            EmptyContent()
                        } else {
                            ItemUsagesContent(
                                usages = usages,
                                onArmorClick = onArmorClick,
                                onDecorationClick = onDecorationClick,
                                onItemClick = onItemClick,
                                onWeaponClick = onWeaponClick,
                            )
                        }
                    }
                }

                ItemDetailTab.ITEM_SOURCES -> {
                    uiState.item.sources?.let { sources ->
                        if (sources.isEmpty()) {
                            EmptyContent()
                        } else {
                            ItemSourcesContent(
                                sources = sources,
                                onItemClick = onItemClick,
                                onLocationClick = onLocationClick,
                                onMonsterClick = onMonsterClick,
                            )
                        }
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
            initialTab = ItemDetailTab.ITEM_SUMMARY,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            initialTab = ItemDetailTab.ITEM_USAGES,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            initialTab = ItemDetailTab.ITEM_SOURCES,
            item = PreviewItemData.item,
        ),
    )

}
