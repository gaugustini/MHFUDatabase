package com.gaugustini.mhfudatabase.ui.quest.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.HubType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.quest.components.QuestList
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

enum class QuestListTab(@param:StringRes val title: Int) {
    VILLAGE(R.string.tab_quest_village),
    GUILD(R.string.tab_quest_guild);

    companion object {
        val all = QuestListTab.entries

        fun fromIndex(index: Int): QuestListTab = all.getOrElse(index) { VILLAGE }

        fun toIndex(tab: QuestListTab): Int = all.indexOf(tab)

    }
}

@Composable
fun QuestListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onQuestClick: (questId: Int) -> Unit,
    viewModel: QuestListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuestListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onToggleExpand = viewModel::toggleExpansion,
        onQuestClick = onQuestClick,
    )
}

@Composable
fun QuestListScreen(
    uiState: QuestListState = QuestListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onToggleExpand: (numberOfStars: Int, HubType) -> Unit = { _, _ -> },
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = QuestListTab.toIndex(uiState.initialTab),
        pageCount = { QuestListTab.all.size },
    )
    TabbedLayout(
        pagerState = pagerState,
        tabTitles = QuestListTab.all.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_quest_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (QuestListTab.fromIndex(tabIndex)) {
            QuestListTab.VILLAGE -> QuestList(
                quests = uiState.quests.filter { it.hubType == HubType.VILLAGE },
                expandedStarSections = uiState.expandedStarSectionsVillage,
                onToggleExpand = { onToggleExpand(it, HubType.VILLAGE) },
                onQuestClick = onQuestClick,
            )

            QuestListTab.GUILD -> QuestList(
                quests = uiState.quests.filter { it.hubType == HubType.GUILD },
                expandedStarSections = uiState.expandedStarSectionsGuild,
                onToggleExpand = { onToggleExpand(it, HubType.GUILD) },
                onQuestClick = onQuestClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuestListScreenPreview(
    @PreviewParameter(QuestListScreenPreviewParamProvider::class) uiState: QuestListState
) {
    Theme {
        QuestListScreen(uiState)
    }
}

private class QuestListScreenPreviewParamProvider : PreviewParameterProvider<QuestListState> {

    override val values: Sequence<QuestListState> = sequenceOf(
        QuestListState(
            initialTab = QuestListTab.VILLAGE,
            quests = PreviewQuestData.questList,
            expandedStarSectionsVillage = setOf(5),
        ),
    )

}
