package com.gaugustini.mhfudatabase.ui.features.monster.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

enum class MonsterDetailPage {
    SUMMARY,
    DAMAGE,
    REWARD,
    QUEST;
}

@Composable
fun MonsterDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    onQuestClick: (questId: Int) -> Unit,
    viewModel: MonsterDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MonsterDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onChangePage = viewModel::onChangePage,
        onChangeRank = viewModel::onChangeRank,
        onItemClick = onItemClick,
        onQuestClick = onQuestClick,
    )
}

@Composable
fun MonsterDetailScreen(
    uiState: MonsterDetailState = MonsterDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onChangePage: (MonsterDetailPage) -> Unit = {},
    onChangeRank: (rank: Rank) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.monster?.name ?: stringResource(R.string.screen_monster_detail),
                navigationType = NavigationType.BACK,
                navigation = {
                    if (uiState.page == MonsterDetailPage.SUMMARY)
                        navigateBack()
                    else
                        onChangePage(MonsterDetailPage.SUMMARY)
                },
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    if (uiState.page == MonsterDetailPage.REWARD) {
                        MonsterDetailRankFilter(
                            selectedRank = uiState.rewardRank,
                            availableRanks = uiState.availableRewardRanks,
                            onChangeRank = onChangeRank,
                        )
                    }
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.monster != null) {
            when (uiState.page) {
                MonsterDetailPage.SUMMARY -> {
                    MonsterDetailSummaryContent(
                        monster = uiState.monster,
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                MonsterDetailPage.DAMAGE -> {
                    MonsterDetailDamageContent(
                        damage = uiState.monster.damageStats ?: emptyList(),
                        ailments = uiState.monster.ailmentStats ?: emptyList(),
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                MonsterDetailPage.REWARD -> {
                    MonsterDetailRewardContent(
                        rewards = uiState.rewards,
                        onItemClick = onItemClick,
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }

                MonsterDetailPage.QUEST -> {
                    MonsterDetailQuestContent(
                        quests = uiState.monster.quests ?: emptyList(),
                        onQuestClick = onQuestClick,
                        onChangePage = onChangePage,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun MonsterDetailRankFilter(
    selectedRank: Rank?,
    availableRanks: List<Rank>,
    modifier: Modifier = Modifier,
    onChangeRank: (rank: Rank) -> Unit = {},
) {
    if (availableRanks.isEmpty() || selectedRank == null) return

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        availableRanks.forEach { rank ->
            FilterChip(
                selected = rank == selectedRank,
                onClick = { onChangeRank(rank) },
                label = {
                    Text(
                        rank.name.lowercase()
                            .replaceFirstChar { it.uppercase() } // TODO: Change to string resources
                    )
                }
            )
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailScreenPreview(
    @PreviewParameter(MonsterDetailScreenPreviewParameter::class) uiState: MonsterDetailState
) {
    Theme {
        MonsterDetailScreen(uiState)
    }
}

private class MonsterDetailScreenPreviewParameter : PreviewParameterProvider<MonsterDetailState> {

    override val values: Sequence<MonsterDetailState> = sequenceOf(
        MonsterDetailState(
            page = MonsterDetailPage.SUMMARY,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            page = MonsterDetailPage.DAMAGE,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            page = MonsterDetailPage.REWARD,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            page = MonsterDetailPage.QUEST,
            monster = PreviewMonsterData.monster,
        ),
    )

}
