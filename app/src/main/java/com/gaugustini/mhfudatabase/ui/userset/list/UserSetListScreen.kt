package com.gaugustini.mhfudatabase.ui.userset.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.userset.components.UserSetList

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
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_user_set_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onSetClick(0) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.user_set_new),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
            )
        },
    ) { innerPadding ->
        UserSetList(
            sets = uiState.sets,
            onSetClick = onSetClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
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
