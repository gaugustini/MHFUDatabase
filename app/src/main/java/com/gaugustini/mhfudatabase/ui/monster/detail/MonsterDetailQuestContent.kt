package com.gaugustini.mhfudatabase.ui.monster.detail

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun MonsterDetailQuestContent(
    quests: List<Quest>,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(quests) { index, quest ->
            MonsterDetailQuestListItem(
                quest = quest,
                onQuestClick = onQuestClick,
            )
            if (index != quests.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun MonsterDetailQuestListItem(
    quest: Quest,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            QuestIcon(
                goalType = quest.goalType,
                modifier = Modifier.size(Dimension.Size.small)
            )
        },
        headlineContent = {
            Text(
                text = quest.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingContent = {
            Text(
                text = stringResource(
                    when (quest.hubType) {
                        HubType.VILLAGE -> R.string.quest_village_stars
                        HubType.GUILD -> R.string.quest_guild_stars
                    },
                    quest.stars
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium
        ),
        modifier = modifier.clickable { onQuestClick(quest.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterDetailQuestContentPreview() {
    Theme {
        MonsterDetailQuestContent(
            quests = PreviewQuestData.questList,
        )
    }
}
