package com.gaugustini.mhfudatabase.ui.features.quest.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.QuestReward
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.quest.components.QuestRewardListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestDetailRewardContent(
    rewards: List<QuestReward>,
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
                QuestRewardListItem(
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
fun QuestDetailRewardContentPreview() {
    Theme {
        QuestDetailRewardContent(
            rewards = PreviewQuestData.questRewardList,
        )
    }
}
