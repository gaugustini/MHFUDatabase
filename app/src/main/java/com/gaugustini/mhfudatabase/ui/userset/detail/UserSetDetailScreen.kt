package com.gaugustini.mhfudatabase.ui.userset.detail

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
fun UserSetDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    viewModel: UserSetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserSetDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
    )
}

@Composable
fun UserSetDetailScreen(
    uiState: UserSetDetailState = UserSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
) {

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserSetDetailScreenPreview(
    @PreviewParameter(UserSetDetailScreenPreviewParamProvider::class) uiState: UserSetDetailState
) {
    Theme {
        UserSetDetailScreen(uiState)
    }
}

private class UserSetDetailScreenPreviewParamProvider : PreviewParameterProvider<UserSetDetailState> {

    override val values: Sequence<UserSetDetailState> = sequenceOf(
        UserSetDetailState(
            set = null,
            weapon = null,
            armors = listOf(),
            decorations = listOf(),
        ),
    )

}
