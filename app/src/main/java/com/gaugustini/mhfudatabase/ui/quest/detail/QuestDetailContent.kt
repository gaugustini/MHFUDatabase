package com.gaugustini.mhfudatabase.ui.quest.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.HubType
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.location.components.LocationListItem
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterListItem
import com.gaugustini.mhfudatabase.ui.quest.components.QuestSummary
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestDetailContent(
    quest: Quest,
    monsters: List<Monster>,
    modifier: Modifier = Modifier,
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                QuestIcon(goalType = quest.goalType)
            },
            title = quest.name,
            subtitle = stringResource(
                when (quest.hubType) {
                    HubType.VILLAGE -> R.string.quest_village_stars
                    HubType.GUILD -> R.string.quest_guild_stars
                },
                quest.stars
            ),
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
        LocationListItem(
            location = Location(
                id = quest.locationId,
                name = quest.locationName,
            ),
            onLocationClick = onLocationClick,
        )

        SectionHeader(
            title = stringResource(R.string.quest_monsters),
        )
        monsters.forEach { monster ->
            MonsterListItem(
                monster = monster,
                onMonsterClick = onMonsterClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuestDetailContentPreview() {
    Theme {
        QuestDetailContent(
            quest = PreviewQuestData.quest,
            monsters = PreviewMonsterData.monsterList,
        )
    }
}
