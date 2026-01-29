package com.gaugustini.mhfudatabase.ui.features.location.list

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
import com.gaugustini.mhfudatabase.ui.features.location.components.LocationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData

@Composable
fun LocationListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onLocationClick: (locationId: Int) -> Unit,
    viewModel: LocationListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LocationListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onLocationClick = onLocationClick,
    )
}

@Composable
fun LocationListScreen(
    uiState: LocationListState = LocationListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_location_list),
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
            items(uiState.locations) { location ->
                LocationListItem(
                    location = location,
                    onLocationClick = onLocationClick,
                )
                HorizontalDivider()
            }
        }
    }
}

@DevicePreviews
@Composable
fun LocationListScreenPreview(
    @PreviewParameter(LocationListScreenPreviewParmProvider::class) uiState: LocationListState
) {
    Theme {
        LocationListScreen(uiState)
    }
}

private class LocationListScreenPreviewParmProvider : PreviewParameterProvider<LocationListState> {

    override val values: Sequence<LocationListState> = sequenceOf(
        LocationListState(
            locations = PreviewLocationData.locationList,
        ),
    )

}
