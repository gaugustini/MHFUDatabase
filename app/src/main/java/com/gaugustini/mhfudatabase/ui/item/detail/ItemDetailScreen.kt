package com.gaugustini.mhfudatabase.ui.item.detail

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

enum class ItemDetailTab(@param:StringRes val title: Int) {
    ITEM_SUMMARY(R.string.tab_item_summary),
    ITEM_USAGES(R.string.tab_item_usages),
    ITEM_SOURCES(R.string.tab_item_sources);

    companion object {
        val all = ItemDetailTab.entries

        fun fromIndex(index: Int): ItemDetailTab = all.getOrElse(index) { ITEM_SUMMARY }

        fun toIndex(tab: ItemDetailTab): Int = all.indexOf(tab)

    }
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
        initialPage = ItemDetailTab.toIndex(uiState.initialTab),
        pageCount = { ItemDetailTab.all.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = ItemDetailTab.all.map { stringResource(it.title) },
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
            when (ItemDetailTab.fromIndex(tabIndex)) {
                ItemDetailTab.ITEM_SUMMARY -> {
                    ItemSummaryContent(
                        item = uiState.item,
                    )
                }

                ItemDetailTab.ITEM_USAGES -> {
                    if (uiState.usages.combinations.isEmpty() &&
                        uiState.usages.armors.isEmpty() &&
                        uiState.usages.weapons.isEmpty()
                    ) {
                        EmptyContent()
                    } else {
                        ItemUsagesContent(
                            combinations = uiState.usages.combinations,
                            armors = uiState.usages.armors,
                            decorations = uiState.usages.decorations,
                            weapons = uiState.usages.weapons,
                            onArmorClick = onArmorClick,
                            onDecorationClick = onDecorationClick,
                            onItemClick = onItemClick,
                            onWeaponClick = onWeaponClick,
                        )
                    }
                }

                ItemDetailTab.ITEM_SOURCES -> {
                    if (uiState.sources.combinations.isEmpty() &&
                        uiState.sources.locations.isEmpty() &&
                        uiState.sources.monsterRewards.isEmpty()
                    ) {
                        EmptyContent()
                    } else {
                        ItemSourcesContent(
                            combinations = uiState.sources.combinations,
                            locations = uiState.sources.locations,
                            monsters = uiState.sources.monsterRewards,
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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
            item = PreviewItemData.item,
        ),
    )

}
