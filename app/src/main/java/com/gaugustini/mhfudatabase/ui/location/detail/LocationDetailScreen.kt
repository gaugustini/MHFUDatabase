package com.gaugustini.mhfudatabase.ui.location.detail

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
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

enum class LocationDetailTab(@param:StringRes val title: Int) {
    LOW_RANK(R.string.tab_location_low_rank),
    HIGH_RANK(R.string.tab_location_high_rank),
    G_RANK(R.string.tab_location_g_rank),
    TREASURE(R.string.tab_location_treasure);

    companion object {
        val all = LocationDetailTab.entries

        fun fromIndex(index: Int): LocationDetailTab = all.getOrElse(index) { LOW_RANK }

        fun toIndex(tab: LocationDetailTab): Int = all.indexOf(tab)

    }
}

@Composable
fun LocationDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: LocationDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LocationDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onItemClick = onItemClick,
    )
}

@Composable
fun LocationDetailScreen(
    uiState: LocationDetailState = LocationDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = LocationDetailTab.toIndex(uiState.initialTab),
        pageCount = { LocationDetailTab.all.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = LocationDetailTab.all.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.location?.name ?: stringResource(R.string.screen_location_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (LocationDetailTab.fromIndex(tabIndex)) {
            LocationDetailTab.LOW_RANK ->
                if (uiState.itemsLowRank.isNotEmpty()) {
                    LocationDetailRankContent(
                        gatheringPoints = uiState.itemsLowRank,
                        onItemClick = onItemClick,
                    )
                } else {
                    EmptyContent()
                }

            LocationDetailTab.HIGH_RANK ->
                if (uiState.itemsHighRank.isNotEmpty()) {
                    LocationDetailRankContent(
                        gatheringPoints = uiState.itemsHighRank,
                        onItemClick = onItemClick,
                    )
                } else {
                    EmptyContent()
                }

            LocationDetailTab.G_RANK ->
                if (uiState.itemsGRank.isNotEmpty()) {
                    LocationDetailRankContent(
                        gatheringPoints = uiState.itemsGRank,
                        onItemClick = onItemClick,
                    )
                } else {
                    EmptyContent()
                }

            LocationDetailTab.TREASURE ->
                if (uiState.itemsTreasure.isNotEmpty()) {
                    LocationDetailRankContent(
                        gatheringPoints = uiState.itemsTreasure,
                        onItemClick = onItemClick,
                    )
                } else {
                    EmptyContent()
                }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationDetailScreenPreview(
    @PreviewParameter(LocationDetailScreenPreviewParamProvider::class) uiState: LocationDetailState
) {
    Theme {
        LocationDetailScreen(uiState)
    }
}

private class LocationDetailScreenPreviewParamProvider :
    PreviewParameterProvider<LocationDetailState> {

    override val values: Sequence<LocationDetailState> = sequenceOf(
        LocationDetailState(
            initialTab = LocationDetailTab.LOW_RANK,
            location = PreviewLocationData.location,
            itemsLowRank = PreviewItemData.itemLocationList,
        ),
    )

}
