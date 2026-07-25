package com.gaugustini.mhfudatabase.ui.features.weapon.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

enum class WeaponDetailPage {
    SUMMARY,
    PATHS;
}

@Composable
fun WeaponDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: WeaponDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeaponDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onChangePage = viewModel::onChangePage,
        onItemClick = onItemClick,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun WeaponDetailScreen(
    uiState: WeaponDetailState = WeaponDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangePage: (WeaponDetailPage) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.weapon?.name ?: stringResource(R.string.screen_weapon_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == WeaponDetailPage.SUMMARY)
                        navigateBack()
                    else
                        onChangePage(WeaponDetailPage.SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.weapon != null) {
            when (uiState.page) {
                WeaponDetailPage.SUMMARY -> {
                    WeaponDetailSummaryContent(
                        weapon = uiState.weapon,
                        onChangePage = onChangePage,
                        onItemClick = onItemClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                WeaponDetailPage.PATHS -> {
                    WeaponDetailPathsContent(
                        paths = uiState.weapon.paths ?: emptyList(),
                        upgrades = uiState.weapon.upgrades ?: emptyList(),
                        finals = uiState.weapon.finals ?: emptyList(),
                        onChangePage = onChangePage,
                        onWeaponClick = onWeaponClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun WeaponDetailScreenPreview(
    @PreviewParameter(WeaponDetailScreenPreviewParameterProvider::class) uiState: WeaponDetailState
) {
    Theme {
        WeaponDetailScreen(uiState)
    }
}

private class WeaponDetailScreenPreviewParameterProvider : PreviewParameterProvider<WeaponDetailState> {

    override val values: Sequence<WeaponDetailState> = sequenceOf(
        WeaponDetailState(
            page = WeaponDetailPage.SUMMARY,
            weapon = PreviewWeaponData.weaponGS,
        ),
        WeaponDetailState(
            page = WeaponDetailPage.PATHS,
            weapon = PreviewWeaponData.weaponGS,
        ),
    )

}
