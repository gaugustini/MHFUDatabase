package com.gaugustini.mhfudatabase.ui.features.veggie.detail

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
import com.gaugustini.mhfudatabase.ui.features.veggie.components.VeggieTradeListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewVeggieData

@Composable
fun VeggieDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: VeggieDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VeggieDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onItemClick = onItemClick,
    )
}

@Composable
fun VeggieDetailScreen(
    uiState: VeggieDetailState = VeggieDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = uiState.veggieLocation?.location?.name
                    ?: stringResource(R.string.screen_veggie_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        uiState.veggieLocation?.trades?.let { trades ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(trades) { trade ->
                    VeggieTradeListItem(
                        veggieTrade = trade,
                        onItemClick = onItemClick,
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun VeggieDetailScreenPreview(
    @PreviewParameter(VeggieDetailScreenPreviewParamProvider::class) uiState: VeggieDetailState
) {
    Theme {
        VeggieDetailScreen(uiState)
    }
}

private class VeggieDetailScreenPreviewParamProvider : PreviewParameterProvider<VeggieDetailState> {

    override val values: Sequence<VeggieDetailState> = sequenceOf(
        VeggieDetailState(
            veggieLocation = PreviewVeggieData.veggieLocation,
        ),
    )

}
