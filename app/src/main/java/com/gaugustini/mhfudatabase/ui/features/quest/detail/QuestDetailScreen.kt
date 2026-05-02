package com.gaugustini.mhfudatabase.ui.features.quest.detail

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
import com.gaugustini.mhfudatabase.ui.components.EmptyContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

enum class QuestDetailTab(@get:StringRes val title: Int) {
    SUMMARY(R.string.tab_quest_detail_summary),
    REWARD(R.string.tab_quest_detail_reward);
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
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { QuestDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = QuestDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.quest?.name ?: stringResource(R.string.screen_quest_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.quest != null) {
            when (QuestDetailTab.entries[tabIndex]) {
                QuestDetailTab.SUMMARY -> {
                    QuestDetailSummaryContent(
                        quest = uiState.quest,
                        onLocationClick = onLocationClick,
                        onMonsterClick = onMonsterClick,
                    )
                }

                QuestDetailTab.REWARD -> {
                    uiState.quest.rewards?.let { rewards ->
                        if (rewards.isEmpty()) {
                            EmptyContent()
                        } else {
                            QuestDetailRewardContent(
                                rewards = rewards,
                                onItemClick = onItemClick,
                            )
                        }
                    }
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
            initialTab = QuestDetailTab.SUMMARY,
            quest = PreviewQuestData.quest,
        ),
        QuestDetailState(
            initialTab = QuestDetailTab.REWARD,
            quest = PreviewQuestData.quest,
        ),
    )

}
