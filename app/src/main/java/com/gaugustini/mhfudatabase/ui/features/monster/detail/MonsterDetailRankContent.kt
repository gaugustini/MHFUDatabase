package com.gaugustini.mhfudatabase.ui.features.monster.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterRewardListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailRankContent(
    rewards: List<MonsterReward>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val rewardsPerCondition = rewards.groupBy { it.condition }

    LazyColumn(
        modifier = modifier
    ) {
        rewardsPerCondition.forEach { (condition, rewards) ->
            item {
                SectionHeader(
                    title = condition,
                )
            }
            itemsIndexed(rewards) { index, reward ->
                MonsterRewardListItem(
                    reward = reward,
                    onItemClick = onItemClick,
                )
                if (index != rewards.lastIndex) {
                    HorizontalDivider()
                }
            }
            item {
                Spacer(Modifier.height(Dimension.Spacing.large))
            }
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailRankContentPreview() {
    Theme {
        MonsterDetailRankContent(
            rewards = PreviewMonsterData.monsterRewardList,
        )
    }
}
