package com.gaugustini.mhfudatabase.ui.features.item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.model.QuestSource
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
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
    val questGroup = when (source.quest.group) {
        QuestGroup.VILLAGE_1 -> R.string.item_quest_group_village_1
        QuestGroup.VILLAGE_2 -> R.string.item_quest_group_village_2
        QuestGroup.VILLAGE_3 -> R.string.item_quest_group_village_3
        QuestGroup.VILLAGE_4 -> R.string.item_quest_group_village_4
        QuestGroup.VILLAGE_5 -> R.string.item_quest_group_village_5
        QuestGroup.VILLAGE_6 -> R.string.item_quest_group_village_6
        QuestGroup.VILLAGE_7 -> R.string.item_quest_group_village_7
        QuestGroup.VILLAGE_8 -> R.string.item_quest_group_village_8
        QuestGroup.VILLAGE_9 -> R.string.item_quest_group_village_9
        QuestGroup.HR_1_1 -> R.string.item_quest_group_hr_1_1
        QuestGroup.HR_1_2 -> R.string.item_quest_group_hr_1_2
        QuestGroup.HR_1_3 -> R.string.item_quest_group_hr_1_3
        QuestGroup.HR_2 -> R.string.item_quest_group_hr_2
        QuestGroup.HR_3 -> R.string.item_quest_group_hr_3
        QuestGroup.HR_4 -> R.string.item_quest_group_hr_4
        QuestGroup.HR_5 -> R.string.item_quest_group_hr_5
        QuestGroup.HR_6 -> R.string.item_quest_group_hr_6
        QuestGroup.HR_7 -> R.string.item_quest_group_hr_7
        QuestGroup.HR_8 -> R.string.item_quest_group_hr_8
        QuestGroup.HR_9 -> R.string.item_quest_group_hr_9
        QuestGroup.TREASURE -> R.string.item_quest_group_treasure
        QuestGroup.EVENT -> R.string.item_quest_group_event
        QuestGroup.BEGINNER_BASIC -> R.string.item_quest_group_beginner_basic
        QuestGroup.BEGINNER_WEAPON -> R.string.item_quest_group_beginner_weapon
        QuestGroup.TRAINING_BATTLE -> R.string.item_quest_group_training_battle
        QuestGroup.TRAINING_SPECIAL -> R.string.item_quest_group_training_special
        QuestGroup.TRAINING_G -> R.string.item_quest_group_training_g
        QuestGroup.GROUP_PRACTICE -> R.string.item_quest_group_group_practice
        QuestGroup.GROUP_CHALLENGE -> R.string.item_quest_group_group_challenge
    }

    ListItemLayout(
        leadingContent = {
            Text(
                text = stringResource(questGroup),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.defaultMinSize(
                    minWidth = Dimension.Size.medium,
                )
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
