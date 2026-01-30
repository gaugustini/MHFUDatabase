package com.gaugustini.mhfudatabase.ui.features.quest.list

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TabbedLayout
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.quest.components.QuestListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

enum class QuestListTab(@get:StringRes val title: Int) {
    VILLAGE(R.string.tab_quest_village),
    GUILD(R.string.tab_quest_guild);
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
        initialPage = uiState.initialTab.ordinal,
        pageCount = { QuestListTab.entries.size },
    )
    TabbedLayout(
        pagerState = pagerState,
        tabTitles = QuestListTab.entries.map { stringResource(it.title) },
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_quest_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { tabIndex ->
        when (QuestListTab.entries[tabIndex]) {
            QuestListTab.VILLAGE -> {
                QuestList(
                    quests = uiState.quests.filter { it.hubType == HubType.VILLAGE },
                    expandedStarSections = uiState.expandedStarSectionsVillage,
                    onToggleExpand = { onToggleExpand(it, HubType.VILLAGE) },
                    onQuestClick = onQuestClick,
                )
            }

            QuestListTab.GUILD -> {
                QuestList(
                    quests = uiState.quests.filter { it.hubType == HubType.GUILD },
                    expandedStarSections = uiState.expandedStarSectionsGuild,
                    onToggleExpand = { onToggleExpand(it, HubType.GUILD) },
                    onQuestClick = onQuestClick,
                )
            }
        }
    }
}

@Composable
fun QuestList(
    quests: List<Quest>,
    expandedStarSections: Set<Int>,
    modifier: Modifier = Modifier,
    onToggleExpand: (numberOfStars: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val questsPerStar = quests.groupBy { it.stars }

    LazyColumn(
        modifier = modifier
    ) {
        questsPerStar.forEach { (star, quests) ->
            val isExpanded = star in expandedStarSections

            stickyHeader {
                QuestStarSectionHeader(
                    numberOfStars = star,
                    expanded = isExpanded,
                    onToggleExpand = { onToggleExpand(star) }
                )
            }

            items(quests) { quest ->
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column {
                        QuestListItem(
                            quest = quest,
                            onQuestClick = onQuestClick,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun QuestStarSectionHeader(
    numberOfStars: Int,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onToggleExpand: () -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            Text(
                text = numberOfStars.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = if (expanded) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
        },
        headlineContent = {
            Row {
                repeat(numberOfStars) {
                    Icon(
                        imageVector = Icons.TwoTone.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                }
            }
        },
        trailingContent = {
            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = null,
                tint = if (expanded) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.size(Dimension.Size.extraSmall)
            )
        },
        backgroundColor = if (expanded) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        modifier = modifier.clickable { onToggleExpand() }
    )
}

@DevicePreviews
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
