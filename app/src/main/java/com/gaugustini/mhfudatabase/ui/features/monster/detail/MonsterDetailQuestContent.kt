package com.gaugustini.mhfudatabase.ui.features.monster.detail

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
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
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
    val questGroup = when (quest.group) {
        QuestGroup.VILLAGE_1 -> R.string.monster_quest_group_village_1
        QuestGroup.VILLAGE_2 -> R.string.monster_quest_group_village_2
        QuestGroup.VILLAGE_3 -> R.string.monster_quest_group_village_3
        QuestGroup.VILLAGE_4 -> R.string.monster_quest_group_village_4
        QuestGroup.VILLAGE_5 -> R.string.monster_quest_group_village_5
        QuestGroup.VILLAGE_6 -> R.string.monster_quest_group_village_6
        QuestGroup.VILLAGE_7 -> R.string.monster_quest_group_village_7
        QuestGroup.VILLAGE_8 -> R.string.monster_quest_group_village_8
        QuestGroup.VILLAGE_9 -> R.string.monster_quest_group_village_9
        QuestGroup.HR_1_1 -> R.string.monster_quest_group_hr_1_1
        QuestGroup.HR_1_2 -> R.string.monster_quest_group_hr_1_2
        QuestGroup.HR_1_3 -> R.string.monster_quest_group_hr_1_3
        QuestGroup.HR_2 -> R.string.monster_quest_group_hr_2
        QuestGroup.HR_3 -> R.string.monster_quest_group_hr_3
        QuestGroup.HR_4 -> R.string.monster_quest_group_hr_4
        QuestGroup.HR_5 -> R.string.monster_quest_group_hr_5
        QuestGroup.HR_6 -> R.string.monster_quest_group_hr_6
        QuestGroup.HR_7 -> R.string.monster_quest_group_hr_7
        QuestGroup.HR_8 -> R.string.monster_quest_group_hr_8
        QuestGroup.HR_9 -> R.string.monster_quest_group_hr_9
        QuestGroup.TREASURE -> R.string.monster_quest_group_treasure
        QuestGroup.EVENT -> R.string.monster_quest_group_event
        QuestGroup.BEGINNER_BASIC -> R.string.monster_quest_group_beginner_basic
        QuestGroup.BEGINNER_WEAPON -> R.string.monster_quest_group_beginner_weapon
        QuestGroup.TRAINING_BATTLE -> R.string.monster_quest_group_training_battle
        QuestGroup.TRAINING_SPECIAL -> R.string.monster_quest_group_training_special
        QuestGroup.TRAINING_G -> R.string.monster_quest_group_training_g
        QuestGroup.GROUP_PRACTICE -> R.string.monster_quest_group_group_practice
        QuestGroup.GROUP_CHALLENGE -> R.string.monster_quest_group_group_challenge
    }

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
                text = stringResource(questGroup),
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

@DevicePreviews
@Composable
fun MonsterDetailQuestContentPreview() {
    Theme {
        MonsterDetailQuestContent(
            quests = PreviewQuestData.questList,
        )
    }
}
