package com.gaugustini.mhfudatabase.ui.features.search.components.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun SearchListItem(
    quest: Quest,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val questGroup = when (quest.group) {
        QuestGroup.VILLAGE_1, QuestGroup.VILLAGE_2, QuestGroup.VILLAGE_3, QuestGroup.VILLAGE_4,
        QuestGroup.VILLAGE_5, QuestGroup.VILLAGE_6, QuestGroup.VILLAGE_7, QuestGroup.VILLAGE_8,
        QuestGroup.VILLAGE_9 -> R.string.search_quest_village

        QuestGroup.HR_1_1, QuestGroup.HR_1_2, QuestGroup.HR_1_3, QuestGroup.HR_2, QuestGroup.HR_3,
        QuestGroup.HR_4, QuestGroup.HR_5, QuestGroup.HR_6, QuestGroup.HR_7, QuestGroup.HR_8,
        QuestGroup.HR_9 -> R.string.search_quest_guild

        QuestGroup.TREASURE -> R.string.search_quest_treasure

        QuestGroup.EVENT -> R.string.search_quest_event

        QuestGroup.BEGINNER_BASIC, QuestGroup.BEGINNER_WEAPON,
        QuestGroup.TRAINING_BATTLE, QuestGroup.TRAINING_SPECIAL, QuestGroup.TRAINING_G,
        QuestGroup.GROUP_PRACTICE -> R.string.search_quest_training

        QuestGroup.GROUP_CHALLENGE -> R.string.search_quest_challenge
    }

    Column(
        modifier = modifier.clickable { onQuestClick(quest.id) }
    ) {
        ListItemLayout(
            leadingContent = {
                QuestIcon(
                    goalType = quest.goalType,
                    modifier = Modifier.size(SearchListItemDefaults.IconSize)
                )
            },
            headlineContent = {
                Text(
                    text = quest.name,
                    style = SearchListItemDefaults.HeadlineTextStyle,
                    color = SearchListItemDefaults.HeadlineTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Text(
                    text = stringResource(questGroup),
                    style = SearchListItemDefaults.TrailingTextStyle,
                    color = SearchListItemDefaults.TrailingTextColor,
                )
            },
            contentPadding = PaddingValues(
                horizontal = SearchListItemDefaults.HorizontalPadding,
                vertical = SearchListItemDefaults.VerticalPadding,
            ),
        )
        HorizontalDivider()
    }
}

@DevicePreviews
@Composable
fun SearchListItemQuestPreview() {
    Theme {
        SearchListItem(
            quest = PreviewQuestData.quest,
        )
    }
}
