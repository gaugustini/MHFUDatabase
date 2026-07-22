package com.gaugustini.mhfudatabase.ui.features.quest.detail

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
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

enum class QuestDetailPage {
    SUMMARY,
    SUPPLY_BOX,
    REWARD;
}

@Composable
fun QuestDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    onLocationClick: (locationId: Int) -> Unit,
    onMonsterClick: (monsterId: Int) -> Unit,
    viewModel: QuestDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuestDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onChangePage = viewModel::onChangePage,
        onItemClick = onItemClick,
        onLocationClick = onLocationClick,
        onMonsterClick = onMonsterClick,
    )
}

@Composable
fun QuestDetailScreen(
    uiState: QuestDetailState = QuestDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangePage: (QuestDetailPage) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.quest?.name ?: stringResource(R.string.screen_quest_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == QuestDetailPage.SUMMARY)
                        navigateBack()
                    else
                        onChangePage(QuestDetailPage.SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.quest != null) {
            when (uiState.page) {
                QuestDetailPage.SUMMARY -> {
                    QuestDetailSummaryContent(
                        quest = uiState.quest,
                        onChangePage = onChangePage,
                        onLocationClick = onLocationClick,
                        onMonsterClick = onMonsterClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                QuestDetailPage.SUPPLY_BOX -> {
                    QuestDetailSupplyContent(
                        supplies = uiState.quest.supplies ?: emptyList(),
                        onChangePage = onChangePage,
                        onItemClick = onItemClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                QuestDetailPage.REWARD -> {
                    QuestDetailRewardContent(
                        rewards = uiState.quest.rewards ?: emptyList(),
                        onChangePage = onChangePage,
                        onItemClick = onItemClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun QuestDetailPreview(
    @PreviewParameter(QuestDetailScreenPreviewParamProvider::class) uiState: QuestDetailState
) {
    Theme {
        QuestDetailScreen(uiState)
    }
}

private class QuestDetailScreenPreviewParamProvider : PreviewParameterProvider<QuestDetailState> {

    override val values: Sequence<QuestDetailState> = sequenceOf(
        QuestDetailState(
            page = QuestDetailPage.SUMMARY,
            quest = PreviewQuestData.quest,
        ),
        QuestDetailState(
            page = QuestDetailPage.REWARD,
            quest = PreviewQuestData.quest,
        ),
    )

}
