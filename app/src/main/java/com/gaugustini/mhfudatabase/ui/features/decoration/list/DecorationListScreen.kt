package com.gaugustini.mhfudatabase.ui.features.decoration.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.decoration.components.DecorationList
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@Composable
fun DecorationListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onDecorationClick: (decorationId: Int) -> Unit,
    viewModel: DecorationListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DecorationListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onDecorationClick = onDecorationClick,
    )
}

@Composable
fun DecorationListScreen(
    uiState: DecorationListState = DecorationListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_decoration_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        DecorationList(
            decorations = uiState.decorations,
            onDecorationClick = onDecorationClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DecorationListScreenPreview(
    @PreviewParameter(DecorationListScreenPreviewParmProvider::class) uiState: DecorationListState
) {
    Theme {
        DecorationListScreen(uiState)
    }
}

private class DecorationListScreenPreviewParmProvider :
    PreviewParameterProvider<DecorationListState> {

    override val values: Sequence<DecorationListState> = sequenceOf(
        DecorationListState(
            decorations = PreviewDecorationData.decorationList
        ),
    )

}
