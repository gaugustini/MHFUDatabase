package com.gaugustini.mhfudatabase.ui.features.weapon.detail

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

enum class WeaponDetailTab(@param:StringRes val title: Int) {
    SUMMARY(R.string.tab_weapon_detail_summary),
    PATHS(R.string.tab_weapon_detail_paths);

    companion object {
        val all = WeaponDetailTab.entries

        fun fromIndex(index: Int): WeaponDetailTab = all.getOrElse(index) { SUMMARY }

        fun toIndex(tab: WeaponDetailTab): Int = all.indexOf(tab)

    }
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
        onItemClick = onItemClick,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun WeaponDetailScreen(
    uiState: WeaponDetailState = WeaponDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = WeaponDetailTab.toIndex(uiState.initialTab),
        pageCount = { WeaponDetailTab.all.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = WeaponDetailTab.all.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.weapon?.name ?: stringResource(R.string.screen_weapon_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.weapon != null) {
            when (WeaponDetailTab.fromIndex(tabIndex)) {
                WeaponDetailTab.SUMMARY -> WeaponDetailSummaryContent(
                    weapon = uiState.weapon,
                    onItemClick = onItemClick,
                )

                WeaponDetailTab.PATHS -> WeaponDetailPathsContent(
                    paths = uiState.weapon.paths ?: emptyList(),
                    finals = uiState.weapon.finals ?: emptyList(),
                    onWeaponClick = onWeaponClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponDetailScreenPreview(
    @PreviewParameter(WeaponDetailScreenPreviewParameterProvider::class)
    uiState: WeaponDetailState
) {
    Theme {
        WeaponDetailScreen(uiState)
    }
}

private class WeaponDetailScreenPreviewParameterProvider :
    PreviewParameterProvider<WeaponDetailState> {

    override val values: Sequence<WeaponDetailState> = sequenceOf(
        WeaponDetailState(
            initialTab = WeaponDetailTab.SUMMARY,
            weapon = PreviewWeaponData.weaponGS,
        ),
        WeaponDetailState(
            initialTab = WeaponDetailTab.PATHS,
            weapon = PreviewWeaponData.weaponGS,
        ),
    )

}
