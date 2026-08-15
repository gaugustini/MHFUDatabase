package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
        onChangeArea = viewModel::onChangeArea,
        onItemClick = onItemClick,
    )
}

@Composable
fun LocationDetailScreen(
    uiState: LocationDetailState = LocationDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangeRank: (rank: Rank) -> Unit = {},
    onChangeArea: (area: Int) -> Unit = {},
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
                        selectedArea = uiState.area,
                        availableRanks = uiState.availableRanks,
                        availableAreas = uiState.availableAreas,
                        onChangeRank = onChangeRank,
                        onChangeArea = onChangeArea,
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
    selectedArea: Int?,
    availableRanks: List<Rank>,
    availableAreas: List<Int>,
    modifier: Modifier = Modifier,
    onChangeRank: (rank: Rank) -> Unit = {},
    onChangeArea: (area: Int) -> Unit = {},
) {
    if (availableRanks.isEmpty() || selectedRank == null) return

    var rankMenuExpanded by remember { mutableStateOf(false) }
    var areaMenuExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        Box {
            FilterChip(
                selected = true,
                onClick = { rankMenuExpanded = true },
                label = {
                    Text(
                        text = stringResource(
                            when (selectedRank) {
                                Rank.UNRANKED -> R.string.location_filter_rank_unranked
                                Rank.LOW -> R.string.location_filter_rank_low
                                Rank.HIGH -> R.string.location_filter_rank_high
                                Rank.G -> R.string.location_filter_rank_g
                                Rank.TREASURE -> R.string.location_filter_rank_treasure
                                Rank.TRAINING -> R.string.location_filter_rank_training
                            }
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                },
            )

            DropdownMenu(
                expanded = rankMenuExpanded,
                onDismissRequest = { rankMenuExpanded = false }
            ) {
                availableRanks.forEach { rank ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(
                                    when (rank) {
                                        Rank.UNRANKED -> R.string.location_filter_rank_unranked
                                        Rank.LOW -> R.string.location_filter_rank_low
                                        Rank.HIGH -> R.string.location_filter_rank_high
                                        Rank.G -> R.string.location_filter_rank_g
                                        Rank.TREASURE -> R.string.location_filter_rank_treasure
                                        Rank.TRAINING -> R.string.location_filter_rank_training
                                    }
                                )
                            )
                        },
                        onClick = {
                            onChangeRank(rank)
                            rankMenuExpanded = false
                        }
                    )
                }
            }
        }

        Box {
            FilterChip(
                selected = true,
                onClick = { areaMenuExpanded = true },
                label = {
                    selectedArea?.let { area ->
                        Text(
                            text = stringResource(
                                when (area) {
                                    -1 -> R.string.location_secret_area
                                    0 -> R.string.location_base_camp
                                    else -> R.string.location_area
                                },
                                area
                            )
                        )
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                },
            )

            DropdownMenu(
                expanded = areaMenuExpanded,
                onDismissRequest = { areaMenuExpanded = false }
            ) {
                availableAreas.forEach { area ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(
                                    when (area) {
                                        -1 -> R.string.location_secret_area
                                        0 -> R.string.location_base_camp
                                        else -> R.string.location_area
                                    },
                                    area
                                )
                            )
                        },
                        onClick = {
                            onChangeArea(area)
                            areaMenuExpanded = false
                        }
                    )
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
            rank = Rank.LOW,
            location = PreviewLocationData.location,
        ),
    )

}
