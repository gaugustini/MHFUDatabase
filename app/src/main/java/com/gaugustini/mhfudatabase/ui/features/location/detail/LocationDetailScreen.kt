package com.gaugustini.mhfudatabase.ui.features.location.detail

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
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.ui.components.EmptyContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

enum class LocationDetailTab(@get:StringRes val title: Int) {
    LOW_RANK(R.string.tab_location_low_rank),
    HIGH_RANK(R.string.tab_location_high_rank),
    G_RANK(R.string.tab_location_g_rank),
    TREASURE(R.string.tab_location_treasure);
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
        initialPage = uiState.initialTab.ordinal,
        pageCount = { LocationDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = LocationDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.location?.name ?: stringResource(R.string.screen_location_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.location != null) {
            when (LocationDetailTab.entries[tabIndex]) {
                LocationDetailTab.LOW_RANK -> {
                    uiState.location.gatheringPoints?.get(Rank.LOW)?.let { points ->
                        if (points.isEmpty()) {
                            EmptyContent()
                        } else {
                            LocationDetailRankContent(
                                gatheringPoints = points,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                LocationDetailTab.HIGH_RANK -> {
                    uiState.location.gatheringPoints?.get(Rank.HIGH)?.let { points ->
                        if (points.isEmpty()) {
                            EmptyContent()
                        } else {
                            LocationDetailRankContent(
                                gatheringPoints = points,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                LocationDetailTab.G_RANK -> {
                    uiState.location.gatheringPoints?.get(Rank.G)?.let { points ->
                        if (points.isEmpty()) {
                            EmptyContent()
                        } else {
                            LocationDetailRankContent(
                                gatheringPoints = points,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                LocationDetailTab.TREASURE -> {
                    uiState.location.gatheringPoints?.get(Rank.TREASURE)?.let { points ->
                        if (points.isEmpty()) {
                            EmptyContent()
                        } else {
                            LocationDetailRankContent(
                                gatheringPoints = points,
                                onItemClick = onItemClick,
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
fun LocationDetailScreenPreview(
    @PreviewParameter(LocationDetailScreenPreviewParamProvider::class) uiState: LocationDetailState
) {
    Theme {
        LocationDetailScreen(uiState)
    }
}

private class LocationDetailScreenPreviewParamProvider : PreviewParameterProvider<LocationDetailState> {

    override val values: Sequence<LocationDetailState> = sequenceOf(
        LocationDetailState(
            initialTab = LocationDetailTab.LOW_RANK,
            location = PreviewLocationData.location,
        ),
    )

}
