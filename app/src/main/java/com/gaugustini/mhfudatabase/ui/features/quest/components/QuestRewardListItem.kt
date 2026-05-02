package com.gaugustini.mhfudatabase.ui.features.quest.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.QuestReward
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ItemIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestRewardListItem(
    reward: QuestReward,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            ItemIcon(
                type = reward.item.iconType,
                color = reward.item.iconColor,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Row {
                Text(
                    text = reward.item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(Dimension.Spacing.medium))
                Text(
                    text = "x ${reward.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingContent = {
            Text(
                text = "${reward.percentage}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onItemClick(reward.item.id) }
    )
}

@DevicePreviews
@Composable
fun QuestRewardListItemPreview() {
    Theme {
        QuestRewardListItem(
            reward = PreviewQuestData.questReward,
        )
    }
}
