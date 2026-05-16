package com.gaugustini.mhfudatabase.ui.features.veggie.list

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
import com.gaugustini.mhfudatabase.ui.features.veggie.components.VeggieLocationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewVeggieData

@Composable
fun VeggieListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onVeggieLocationClick: (tableId: Int) -> Unit,
    viewModel: VeggieListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VeggieListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onVeggieLocationClick = onVeggieLocationClick,
    )
}

@Composable
fun VeggieListScreen(
    uiState: VeggieListState = VeggieListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onVeggieLocationClick: (tableId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_veggie_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.veggieLocations) { location ->
                VeggieLocationListItem(
                    veggieLocation = location,
                    onVeggieLocationClick = onVeggieLocationClick,
                )
                HorizontalDivider()
            }
        }
    }
}

@DevicePreviews
@Composable
fun VeggieListScreenPreview(
    @PreviewParameter(VeggieListScreenPreviewParamProvider::class) uiState: VeggieListState
) {
    Theme {
        VeggieListScreen(uiState)
    }
}

private class VeggieListScreenPreviewParamProvider : PreviewParameterProvider<VeggieListState> {

    override val values: Sequence<VeggieListState> = sequenceOf(
        VeggieListState(
            veggieLocations = PreviewVeggieData.veggieLocationList,
        ),
    )

}
