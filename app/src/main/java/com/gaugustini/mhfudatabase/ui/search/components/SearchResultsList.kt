package com.gaugustini.mhfudatabase.ui.search.components

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.SearchResults
import com.gaugustini.mhfudatabase.ui.search.components.listitem.SearchListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewLocationData
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun SearchResultsList(
    results: SearchResults,
    modifier: Modifier = Modifier,
    onArmorClick: (armorId: Int) -> Unit = {},
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(results.locations) { location ->
            SearchListItem(
                location = location,
                onLocationClick = onLocationClick,
            )
        }
        items(results.monsters) { monster ->
            SearchListItem(
                monster = monster,
                onMonsterClick = onMonsterClick,
            )
        }
        items(results.skillTrees) { skillTree ->
            SearchListItem(
                skillTree = skillTree,
                onSkillTreeClick = onSkillTreeClick,
            )
        }
        items(results.skills) { skill ->
            SearchListItem(
                skill = skill,
                onSkillClick = onSkillClick,
            )
        }
        items(results.quests) { quest ->
            SearchListItem(
                quest = quest,
                onQuestClick = onQuestClick,
            )
        }
        items(results.items) { item ->
            SearchListItem(
                item = item,
                onItemClick = onItemClick,
            )
        }
        items(results.decorations) { decoration ->
            SearchListItem(
                decoration = decoration,
                onDecorationClick = onDecorationClick,
            )
        }
        items(results.armors) { armor ->
            SearchListItem(
                armor = armor,
                onArmorClick = onArmorClick,
            )
        }
        items(results.weapons) { weapon ->
            SearchListItem(
                weapon = weapon,
                onWeaponClick = onWeaponClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchResultsListPreview() {
    Theme {
        SearchResultsList(
            SearchResults(
                armors = listOf(PreviewArmorData.armor),
                decorations = listOf(PreviewDecorationData.decoration),
                items = listOf(PreviewItemData.item),
                locations = listOf(PreviewLocationData.location),
                monsters = listOf(PreviewMonsterData.monster),
                quests = listOf(PreviewQuestData.quest),
                skillTrees = listOf(PreviewSkillData.skillTree),
                skills = listOf(PreviewSkillData.skill),
                weapons = listOf(PreviewWeaponData.weapon),
            )
        )
    }
}
