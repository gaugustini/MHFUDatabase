package com.gaugustini.mhfudatabase.ui.userset.list

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun UserSetListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onSetClick: (setId: Int) -> Unit,
    viewModel: UserSetListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSetListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onSetClick = onSetClick,
    )
}

@Composable
fun UserSetListScreen(
    uiState: UserSetListState = UserSetListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onSetClick: (setId: Int) -> Unit = {},
) {

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetListScreenPreview(
    @PreviewParameter(UserSetListScreenPreviewParamProvider::class) uiState: UserSetListState
) {
    Theme {
        UserSetListScreen(uiState)
    }
}

private class UserSetListScreenPreviewParamProvider : PreviewParameterProvider<UserSetListState> {

    override val values: Sequence<UserSetListState> = sequenceOf(
        UserSetListState(
            sets = listOf(),
        ),
    )

}
