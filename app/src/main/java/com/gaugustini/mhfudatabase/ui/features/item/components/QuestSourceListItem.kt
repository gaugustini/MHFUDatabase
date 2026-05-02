package com.gaugustini.mhfudatabase.ui.features.item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.QuestSource
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun QuestSourceListItem(
    source: QuestSource,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            QuestIcon(
                goalType = source.quest.goalType,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = source.quest.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Text(
                text = source.condition,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            Text(
                text = "${source.percentage}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onQuestClick(source.quest.id) }
    )
}

@DevicePreviews
@Composable
fun QuestSourceListItemPreview() {
    Theme {
        QuestSourceListItem(
            source = PreviewItemData.questSource,
        )
    }
}
