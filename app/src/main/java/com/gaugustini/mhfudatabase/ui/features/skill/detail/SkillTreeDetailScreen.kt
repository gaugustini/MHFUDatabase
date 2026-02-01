package com.gaugustini.mhfudatabase.ui.features.skill.detail

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
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

enum class SkillTreeDetailTab(@get:StringRes val title: Int) {
    SKILL_TREE_SUMMARY(R.string.tab_skill_detail_summary),
    SKILL_TREE_EQUIPMENT(R.string.tab_skill_detail_equipment);
}

@Composable
fun SkillTreeDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onDecorationClick: (decorationId: Int) -> Unit,
    viewModel: SkillTreeDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SkillTreeDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
    )
}

@Composable
fun SkillTreeDetailScreen(
    uiState: SkillTreeDetailState = SkillTreeDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { SkillTreeDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = SkillTreeDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.skillTree?.name ?: stringResource(R.string.screen_skill_tree_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.skillTree != null) {
            when (SkillTreeDetailTab.entries[tabIndex]) {
                SkillTreeDetailTab.SKILL_TREE_SUMMARY -> {
                    SkillTreeSummaryContent(
                        skillTree = uiState.skillTree,
                    )
                }

                SkillTreeDetailTab.SKILL_TREE_EQUIPMENT -> {
                    SkillTreeEquipmentContent(
                        decorations = uiState.decorations,
                        armors = uiState.armors,
                        onArmorClick = onArmorClick,
                        onDecorationClick = onDecorationClick,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun SkillTreeDetailPreview(
    @PreviewParameter(SkillTreeDetailScreenPreviewParameterProvider::class) uiState: SkillTreeDetailState
) {
    Theme {
        SkillTreeDetailScreen(uiState)
    }
}

private class SkillTreeDetailScreenPreviewParameterProvider :
    PreviewParameterProvider<SkillTreeDetailState> {

    override val values: Sequence<SkillTreeDetailState> = sequenceOf(
        SkillTreeDetailState(
            initialTab = SkillTreeDetailTab.SKILL_TREE_SUMMARY,
            skillTree = PreviewSkillData.skillTree,
        ),
        SkillTreeDetailState(
            initialTab = SkillTreeDetailTab.SKILL_TREE_EQUIPMENT,
            decorations = PreviewDecorationData.decorationList,
            armors = PreviewArmorData.armorList,
        )
    )

}
