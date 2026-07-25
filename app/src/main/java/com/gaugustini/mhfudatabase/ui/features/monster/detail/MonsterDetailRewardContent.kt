package com.gaugustini.mhfudatabase.ui.features.monster.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.domain.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterRewardListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailRewardContent(
    rewards: List<MonsterReward>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onChangePage: (MonsterDetailPage) -> Unit = {},
) {
    BackHandler {
        onChangePage(MonsterDetailPage.SUMMARY)
    }

    val rewardsPerCondition = rewards.groupBy { it.condition }
    var expandedConditions by rememberSaveable(rewardsPerCondition.keys) {
        mutableStateOf(rewardsPerCondition.keys.toSet())
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        rewardsPerCondition.forEach { (condition, rewards) ->
            val isConditionExpanded = condition in expandedConditions

            item {
                SectionHeader(
                    title = condition,
                    isExpandable = true,
                    expanded = isConditionExpanded,
                    modifier = Modifier
                        .padding(bottom = if (isConditionExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (isConditionExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (isConditionExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable {
                            expandedConditions =
                                if (isConditionExpanded) expandedConditions - condition else expandedConditions + condition
                        }
                )
            }

            if (isConditionExpanded) {
                itemsIndexed(
                    items = rewards,
                    key = { _, reward -> "reward_${reward.item.id}_${reward.condition}_${reward.percentage}" }
                ) { index, reward ->
                    val isLastItemInCondition = index == rewards.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItemInCondition) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItemInCondition) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        MonsterRewardListItem(
                            reward = reward,
                            onItemClick = onItemClick,
                        )
                        if (!isLastItemInCondition) {
                            AppHDivider()
                        }
                    }
                    if (isLastItemInCondition) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailRewardContentPreview() {
    Theme {
        MonsterDetailRewardContent(
            rewards = PreviewMonsterData.monsterRewardList,
        )
    }
}
