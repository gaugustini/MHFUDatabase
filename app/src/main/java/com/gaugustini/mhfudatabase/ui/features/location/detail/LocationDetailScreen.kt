package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

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
        onChangeRank = viewModel::onChangeRank,
        onItemClick = onItemClick,
    )
}

@Composable
fun LocationDetailScreen(
    uiState: LocationDetailState = LocationDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangeRank: (rank: Rank) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.location?.name ?: stringResource(R.string.screen_location_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    LocationDetailRankFilter(
                        selectedRank = uiState.rank,
                        availableRanks = uiState.availableRanks,
                        onChangeRank = onChangeRank,
                    )
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.location != null) {
            LocationDetailRankContent(
                gatheringPoints = uiState.gatheringPoints,
                onItemClick = onItemClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
fun LocationDetailRankFilter(
    selectedRank: Rank?,
    availableRanks: List<Rank>,
    modifier: Modifier = Modifier,
    onChangeRank: (rank: Rank) -> Unit = {},
) {
    if (availableRanks.isEmpty() || selectedRank == null) return

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        availableRanks.forEach { rank ->
            FilterChip(
                selected = rank == selectedRank,
                onClick = { onChangeRank(rank) },
                label = {
                    Text(
                        rank.name.lowercase()
                            .replaceFirstChar { it.uppercase() } // TODO: Change to string resources
                    )
                }
            )
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
            rank = Rank.LOW,
            location = PreviewLocationData.location,
        ),
    )

}
