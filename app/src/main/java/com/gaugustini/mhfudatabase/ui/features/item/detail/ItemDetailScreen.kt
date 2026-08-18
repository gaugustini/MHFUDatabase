package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.AnimatedPageContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import kotlinx.coroutines.launch

enum class ItemDetailPage {
    SUMMARY,
    USAGES,
    SOURCES;
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
    val summaryScrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val handlePageChange: (ItemDetailPage) -> Unit = { newPage ->
        if (uiState.page != newPage) {
            if (scrollBehavior.state.heightOffset < 0f) {
                scope.launch {
                    val anim = Animatable(scrollBehavior.state.heightOffset)
                    anim.animateTo(0f, animationSpec = tween(durationMillis = 400)) {
                        scrollBehavior.state.heightOffset = this.value
                    }
                }
            }
            onChangePage(newPage)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.item?.name ?: stringResource(R.string.screen_item_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == ItemDetailPage.SUMMARY)
                        navigateBack()
                    else
                        handlePageChange(ItemDetailPage.SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.item != null) {
            AnimatedPageContent(
                targetState = uiState.page,
                indexMapper = { it.ordinal },
                modifier = Modifier.fillMaxSize()
            ) { targetPage ->
                when (targetPage) {
                    ItemDetailPage.SUMMARY -> {
                        ItemSummaryContent(
                            item = uiState.item,
                            scrollState = summaryScrollState,
                            onChangePage = handlePageChange,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }

                    ItemDetailPage.USAGES -> {
                        uiState.item.usages?.let { usages ->
                            ItemUsagesContent(
                                usages = usages,
                                onArmorClick = onArmorClick,
                                onDecorationClick = onDecorationClick,
                                onItemClick = onItemClick,
                                onWeaponClick = onWeaponClick,
                                onChangePage = handlePageChange,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }

                    ItemDetailPage.SOURCES -> {
                        uiState.item.sources?.let { sources ->
                            ItemSourcesContent(
                                sources = sources,
                                onItemClick = onItemClick,
                                onLocationClick = onLocationClick,
                                onMonsterClick = onMonsterClick,
                                onQuestClick = onQuestClick,
                                onChangePage = handlePageChange,
                                modifier = Modifier.padding(innerPadding),
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
            page = ItemDetailPage.SUMMARY,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            page = ItemDetailPage.USAGES,
            item = PreviewItemData.item,
        ),
        ItemDetailState(
            page = ItemDetailPage.SOURCES,
            item = PreviewItemData.item,
        ),
    )

}
