package com.gaugustini.mhfudatabase.ui.features.skill.detail

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
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

enum class SkillTreeDetailPage {
    SUMMARY,
    EQUIPMENT;
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
        onChangePage = viewModel::onChangePage,
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
    )
}

@Composable
fun SkillTreeDetailScreen(
    uiState: SkillTreeDetailState = SkillTreeDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangePage: (SkillTreeDetailPage) -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.skillTree?.name ?: stringResource(R.string.screen_skill_tree_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == SkillTreeDetailPage.SUMMARY)
                        navigateBack()
                    else
                        onChangePage(SkillTreeDetailPage.SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.skillTree != null) {
            when (uiState.page) {
                SkillTreeDetailPage.SUMMARY -> {
                    SkillTreeSummaryContent(
                        skillTree = uiState.skillTree,
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                SkillTreeDetailPage.EQUIPMENT -> {
                    SkillTreeEquipmentContent(
                        decorations = uiState.decorations,
                        armors = uiState.armors,
                        onChangePage = onChangePage,
                        onArmorClick = onArmorClick,
                        onDecorationClick = onDecorationClick,
                        modifier = Modifier.padding(innerPadding),
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
            page = SkillTreeDetailPage.SUMMARY,
            skillTree = PreviewSkillData.skillTree,
        ),
        SkillTreeDetailState(
            page = SkillTreeDetailPage.EQUIPMENT,
            decorations = PreviewDecorationData.decorationList,
            armors = PreviewArmorData.armorList,
        )
    )

}
