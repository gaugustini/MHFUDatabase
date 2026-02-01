package com.gaugustini.mhfudatabase.ui.features.monster.detail

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
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.ui.components.EmptyContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

enum class MonsterDetailTab(@get:StringRes val title: Int) {
    SUMMARY(R.string.tab_monster_detail_summary),
    DAMAGE(R.string.tab_monster_detail_damage),
    LOW_RANK(R.string.tab_monster_detail_low_rank),
    HIGH_RANK(R.string.tab_monster_detail_high_rank),
    G_RANK(R.string.tab_monster_detail_g_rank),
    QUEST(R.string.tab_monster_detail_quest);
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
        onItemClick = onItemClick,
        onQuestClick = onQuestClick,
    )
}

@Composable
fun MonsterDetailScreen(
    uiState: MonsterDetailState = MonsterDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.initialTab.ordinal,
        pageCount = { MonsterDetailTab.entries.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = MonsterDetailTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = uiState.monster?.name ?: stringResource(R.string.screen_monster_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        if (uiState.monster != null) {
            when (MonsterDetailTab.entries[tabIndex]) {
                MonsterDetailTab.SUMMARY -> {
                    MonsterDetailSummaryContent(
                        monster = uiState.monster,
                    )
                }

                MonsterDetailTab.DAMAGE -> {
                    MonsterDetailDamageContent(
                        damage = uiState.monster.damageStats ?: emptyList(),
                        ailments = uiState.monster.ailmentStats ?: emptyList(),
                    )
                }

                MonsterDetailTab.LOW_RANK -> {
                    uiState.monster.rewards?.get(Rank.LOW).let { rewards ->
                        if (rewards?.isEmpty() ?: true) {
                            EmptyContent()
                        } else {
                            MonsterDetailRankContent(
                                rewards = rewards,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                MonsterDetailTab.HIGH_RANK -> {
                    uiState.monster.rewards?.get(Rank.HIGH).let { rewards ->
                        if (rewards?.isEmpty() ?: true) {
                            EmptyContent()
                        } else {
                            MonsterDetailRankContent(
                                rewards = rewards,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                MonsterDetailTab.G_RANK -> {
                    uiState.monster.rewards?.get(Rank.G).let { rewards ->
                        if (rewards?.isEmpty() ?: true) {
                            EmptyContent()
                        } else {
                            MonsterDetailRankContent(
                                rewards = rewards,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }

                MonsterDetailTab.QUEST -> {
                    uiState.monster.quests.let { quests ->
                        if (quests?.isEmpty() ?: true) {
                            EmptyContent()
                        } else {
                            MonsterDetailQuestContent(
                                quests = quests,
                                onQuestClick = onQuestClick,
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
            initialTab = MonsterDetailTab.SUMMARY,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            initialTab = MonsterDetailTab.DAMAGE,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            initialTab = MonsterDetailTab.LOW_RANK,
            monster = PreviewMonsterData.monster,
        ),
        MonsterDetailState(
            initialTab = MonsterDetailTab.QUEST,
            monster = PreviewMonsterData.monster,
        ),
    )

}
