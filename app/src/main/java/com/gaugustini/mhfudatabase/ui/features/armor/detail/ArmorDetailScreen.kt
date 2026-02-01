package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

enum class ArmorDetailTab(@get:StringRes val title: Int) {
    ARMOR_DETAIL(R.string.tab_armor_detail),
    ARMOR_SET_DETAIL(R.string.tab_armor_set_detail);
}

@Composable
fun ArmorDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onSkillClick: (skillTreeId: Int) -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: ArmorDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArmorDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onArmorClick = onArmorClick,
        onSkillClick = onSkillClick,
        onItemClick = onItemClick,
    )
}

@Composable
fun ArmorDetailScreen(
    uiState: ArmorDetailState = ArmorDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { ArmorDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = ArmorDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.armor?.name ?: stringResource(R.string.screen_armor_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (ArmorDetailTab.entries[tabIndex]) {
            ArmorDetailTab.ARMOR_DETAIL -> {
                if (uiState.armor != null) {
                    ArmorDetailContent(
                        armor = uiState.armor,
                        onSkillClick = onSkillClick,
                        onItemClick = onItemClick,
                    )
                }
            }

            ArmorDetailTab.ARMOR_SET_DETAIL -> {
                if (uiState.armorSet != null) {
                    ArmorSetDetailContent(
                        armorSet = uiState.armorSet,
                        onArmorClick = onArmorClick,
                        onSkillClick = onSkillClick,
                        onItemClick = onItemClick,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ArmorDetailScreenPreview(
    @PreviewParameter(ArmorDetailScreenPreviewParamProvider::class) uiState: ArmorDetailState
) {
    Theme {
        ArmorDetailScreen(uiState)
    }
}

private class ArmorDetailScreenPreviewParamProvider : PreviewParameterProvider<ArmorDetailState> {

    override val values: Sequence<ArmorDetailState> = sequenceOf(
        ArmorDetailState(
            initialTab = ArmorDetailTab.ARMOR_DETAIL,
            armor = PreviewArmorData.armor,
        ),
        ArmorDetailState(
            initialTab = ArmorDetailTab.ARMOR_SET_DETAIL,
            armorSet = PreviewArmorData.armorSet,
        ),
    )

}
