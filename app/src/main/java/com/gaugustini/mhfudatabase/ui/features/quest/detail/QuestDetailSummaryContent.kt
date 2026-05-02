package com.gaugustini.mhfudatabase.ui.features.quest.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.features.location.components.LocationListItem
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterListItem
import com.gaugustini.mhfudatabase.ui.features.quest.components.QuestSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestDetailSummaryContent(
    quest: Quest,
    modifier: Modifier = Modifier,
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    val questGroup = when (quest.group) {
        QuestGroup.VILLAGE_1 -> R.string.detail_quest_group_village_1
        QuestGroup.VILLAGE_2 -> R.string.detail_quest_group_village_2
        QuestGroup.VILLAGE_3 -> R.string.detail_quest_group_village_3
        QuestGroup.VILLAGE_4 -> R.string.detail_quest_group_village_4
        QuestGroup.VILLAGE_5 -> R.string.detail_quest_group_village_5
        QuestGroup.VILLAGE_6 -> R.string.detail_quest_group_village_6
        QuestGroup.VILLAGE_7 -> R.string.detail_quest_group_village_7
        QuestGroup.VILLAGE_8 -> R.string.detail_quest_group_village_8
        QuestGroup.VILLAGE_9 -> R.string.detail_quest_group_village_9
        QuestGroup.HR_1_1 -> R.string.detail_quest_group_hr_1_1
        QuestGroup.HR_1_2 -> R.string.detail_quest_group_hr_1_2
        QuestGroup.HR_1_3 -> R.string.detail_quest_group_hr_1_3
        QuestGroup.HR_2 -> R.string.detail_quest_group_hr_2
        QuestGroup.HR_3 -> R.string.detail_quest_group_hr_3
        QuestGroup.HR_4 -> R.string.detail_quest_group_hr_4
        QuestGroup.HR_5 -> R.string.detail_quest_group_hr_5
        QuestGroup.HR_6 -> R.string.detail_quest_group_hr_6
        QuestGroup.HR_7 -> R.string.detail_quest_group_hr_7
        QuestGroup.HR_8 -> R.string.detail_quest_group_hr_8
        QuestGroup.HR_9 -> R.string.detail_quest_group_hr_9
        QuestGroup.TREASURE -> R.string.detail_quest_group_treasure
        QuestGroup.EVENT -> R.string.detail_quest_group_event
        QuestGroup.BEGINNER_BASIC -> R.string.detail_quest_group_beginner_basic
        QuestGroup.BEGINNER_WEAPON -> R.string.detail_quest_group_beginner_weapon
        QuestGroup.TRAINING_BATTLE -> R.string.detail_quest_group_training_battle
        QuestGroup.TRAINING_SPECIAL -> R.string.detail_quest_group_training_special
        QuestGroup.TRAINING_G -> R.string.detail_quest_group_training_g
        QuestGroup.GROUP_PRACTICE -> R.string.detail_quest_group_group_practice
        QuestGroup.GROUP_CHALLENGE -> R.string.detail_quest_group_group_challenge
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                QuestIcon(goalType = quest.goalType)
            },
            title = quest.name,
            subtitle = stringResource(questGroup),
            description = quest.goal,
        )

        QuestSummary(
            quest = quest,
        )

        SectionHeader(
            title = stringResource(R.string.quest_description),
        )
        Text(
            text = quest.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(Dimension.Padding.large)
        )
        Text(
            text = stringResource(R.string.quest_client, quest.client),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(Dimension.Padding.large)
        )

        SectionHeader(
            title = stringResource(R.string.quest_location),
        )
        quest.location?.let { location ->
            LocationListItem(
                location = location,
                onLocationClick = onLocationClick,
            )
        }

        SectionHeader(
            title = stringResource(R.string.quest_monsters),
        )
        quest.monsters?.forEach { monster ->
            MonsterListItem(
                monster = monster,
                onMonsterClick = onMonsterClick,
            )
        }
    }
}

@DevicePreviews
@Composable
fun QuestDetailSummaryContentPreview() {
    Theme {
        QuestDetailSummaryContent(
            quest = PreviewQuestData.quest,
        )
    }
}
