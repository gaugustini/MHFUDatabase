package com.gaugustini.mhfudatabase.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.domain.model.SearchResults
import com.gaugustini.mhfudatabase.ui.search.components.SearchResultsList
import com.gaugustini.mhfudatabase.ui.search.components.SearchTopBar
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
fun SearchRoute(
    navigateBack: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onDecorationClick: (decorationId: Int) -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    onLocationClick: (locationId: Int) -> Unit,
    onMonsterClick: (monsterId: Int) -> Unit,
    onQuestClick: (questId: Int) -> Unit,
    onSkillTreeClick: (skillTreeId: Int) -> Unit,
    onSkillClick: (skillTreeId: Int) -> Unit,
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onClearQuery = viewModel::onClearQuery,
        navigateBack = navigateBack,
        onArmorClick = onArmorClick,
        onDecorationClick = onDecorationClick,
        onItemClick = onItemClick,
        onLocationClick = onLocationClick,
        onMonsterClick = onMonsterClick,
        onQuestClick = onQuestClick,
        onSkillTreeClick = onSkillTreeClick,
        onSkillClick = onSkillClick,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun SearchScreen(
    uiState: SearchState = SearchState(),
    onQueryChange: (String) -> Unit = {},
    onClearQuery: () -> Unit = {},
    navigateBack: () -> Unit = {},
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
    Scaffold(
        topBar = {
            SearchTopBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                onClearQuery = onClearQuery,
                navigateBack = navigateBack,
            )
        },
    ) { innerPadding ->
        SearchResultsList(
            results = uiState.results,
            onArmorClick = onArmorClick,
            onDecorationClick = onDecorationClick,
            onItemClick = onItemClick,
            onLocationClick = onLocationClick,
            onMonsterClick = onMonsterClick,
            onQuestClick = onQuestClick,
            onSkillTreeClick = onSkillTreeClick,
            onSkillClick = onSkillClick,
            onWeaponClick = onWeaponClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview(
    @PreviewParameter(SearchScreenPreviewParamProvider::class) uiState: SearchState
) {
    Theme {
        SearchScreen(uiState)
    }
}

private class SearchScreenPreviewParamProvider : PreviewParameterProvider<SearchState> {

    override val values: Sequence<SearchState> = sequenceOf(
        SearchState(),
        SearchState(
            query = "query",
            results = SearchResults(
                armors = listOf(PreviewArmorData.armor),
                decorations = listOf(PreviewDecorationData.decoration),
                items = listOf(PreviewItemData.item),
                locations = listOf(PreviewLocationData.location),
                monsters = listOf(PreviewMonsterData.monster),
                quests = listOf(PreviewQuestData.quest),
                skillTrees = listOf(PreviewSkillData.skillTree),
                skills = listOf(PreviewSkillData.skill),
                weapons = listOf(PreviewWeaponData.weapon),
            ),
        ),
    )

}
