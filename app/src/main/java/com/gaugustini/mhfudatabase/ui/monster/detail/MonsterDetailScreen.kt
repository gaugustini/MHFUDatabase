package com.gaugustini.mhfudatabase.ui.monster.detail

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
import com.gaugustini.mhfudatabase.ui.components.EmptyContent
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

enum class MonsterDetailTab(@param:StringRes val title: Int) {
    SUMMARY(R.string.tab_monster_detail_summary),
    DAMAGE(R.string.tab_monster_detail_damage),
    LOW_RANK(R.string.tab_monster_detail_low_rank),
    HIGH_RANK(R.string.tab_monster_detail_high_rank),
    G_RANK(R.string.tab_monster_detail_g_rank),
    QUEST(R.string.tab_monster_detail_quest);

    companion object {
        val all = MonsterDetailTab.entries

        fun fromIndex(index: Int): MonsterDetailTab = all.getOrElse(index) { SUMMARY }

        fun toIndex(tab: MonsterDetailTab): Int = all.indexOf(tab)

    }
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
        initialPage = MonsterDetailTab.toIndex(uiState.initialTab),
        pageCount = { MonsterDetailTab.all.size },
    )

    TabbedLayout(
        pagerState = pagerState,
        tabTitles = MonsterDetailTab.all.map { stringResource(it.title) },
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
            when (MonsterDetailTab.fromIndex(tabIndex)) {
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
                    if (uiState.rewardsLowRank.isNotEmpty()) {
                        MonsterDetailRankContent(
                            rewards = uiState.rewardsLowRank,
                            onItemClick = onItemClick,
                        )
                    } else {
                        EmptyContent()
                    }
                }

                MonsterDetailTab.HIGH_RANK -> {
                    if (uiState.rewardsHighRank.isNotEmpty()) {
                        MonsterDetailRankContent(
                            rewards = uiState.rewardsHighRank,
                            onItemClick = onItemClick,
                        )
                    } else {
                        EmptyContent()
                    }
                }

                MonsterDetailTab.G_RANK -> {
                    if (uiState.rewardsGRank.isNotEmpty()) {
                        MonsterDetailRankContent(
                            rewards = uiState.rewardsGRank,
                            onItemClick = onItemClick,
                        )
                    } else {
                        EmptyContent()
                    }
                }

                MonsterDetailTab.QUEST -> {
                    if (uiState.monster.quests != null) {
                        MonsterDetailQuestContent(
                            quests = uiState.monster.quests ?: emptyList(),
                            onQuestClick = onQuestClick,
                        )
                    } else {
                        EmptyContent()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterDetailScreenPreview(
    @PreviewParameter(MonsterDetailScreenPreviewParameter::class) uiState: MonsterDetailState,
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
            rewardsLowRank = PreviewMonsterData.monsterRewardList,
        ),
        MonsterDetailState(
            initialTab = MonsterDetailTab.QUEST,
            monster = PreviewMonsterData.monster,
        )
    )

}
