package com.gaugustini.mhfudatabase.ui.monster.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterRewardList(
    rewards: List<MonsterReward>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        rewards.forEachIndexed { index, reward ->
            MonsterRewardListItem(
                reward = reward,
                onItemClick = onItemClick,
            )
            if (index != rewards.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun MonsterRewardListItem(
    reward: MonsterReward,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ItemIcon(
                type = reward.itemIconType,
                color = reward.itemIconColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Row {
                Text(
                    text = reward.itemName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(Dimension.Spacing.medium))
                Text(
                    text = "x ${reward.stackSize}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingContent = {
            if (reward.percentage != null) {
                Text(
                    text = "${reward.percentage}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onItemClick(reward.itemId) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterRewardListPreview() {
    Theme {
        MonsterRewardList(
            rewards = PreviewMonsterData.monsterRewardList,
        )
    }
}
