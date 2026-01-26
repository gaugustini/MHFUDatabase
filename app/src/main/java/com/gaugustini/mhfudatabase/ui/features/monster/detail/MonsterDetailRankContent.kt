package com.gaugustini.mhfudatabase.ui.features.monster.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterRewardList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
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
            item {
                MonsterRewardList(
                    rewards = rewards,
                    onItemClick = onItemClick,
                )
            }
            item {
                Spacer(Modifier.height(Dimension.Spacing.large))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterDetailRankContentPreview() {
    Theme {
        MonsterDetailRankContent(
            rewards = PreviewMonsterData.monsterRewardList,
        )
    }
}
