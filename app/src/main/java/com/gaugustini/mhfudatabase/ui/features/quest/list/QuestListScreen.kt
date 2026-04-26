package com.gaugustini.mhfudatabase.ui.features.quest.list

import androidx.annotation.StringRes
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
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
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
    GUILD(R.string.tab_quest_guild),
    TRAINING(R.string.tab_quest_training);
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
        onQuestClick = onQuestClick,
    )
}

@Composable
fun QuestListScreen(
    uiState: QuestListState = QuestListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
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
                    onQuestClick = onQuestClick,
                )
            }

            QuestListTab.GUILD -> {
                QuestList(
                    quests = uiState.quests.filter { it.hubType == HubType.GUILD },
                    onQuestClick = onQuestClick,
                )
            }

            QuestListTab.TRAINING -> {
                QuestList(
                    quests = uiState.quests.filter { it.hubType == HubType.TRAINING },
                    onQuestClick = onQuestClick,
                )
            }
        }
    }
}

@Composable
fun QuestList(
    quests: List<Quest>,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val questGrouped = quests.groupBy { it.group }
    val expandedGroupSections = remember { mutableStateSetOf<QuestGroup>() }

    val excludedStars = listOf(
        QuestGroup.TREASURE,
        QuestGroup.EVENT,
        QuestGroup.BEGINNER_BASIC,
        QuestGroup.BEGINNER_WEAPON,
        QuestGroup.TRAINING_BATTLE,
        QuestGroup.TRAINING_SPECIAL,
        QuestGroup.TRAINING_G,
        QuestGroup.GROUP_PRACTICE,
        QuestGroup.GROUP_CHALLENGE
    )

    LazyColumn(
        modifier = modifier
    ) {
        questGrouped.forEach { (group, quests) ->
            val isExpanded = group in expandedGroupSections
            val numberOfStars = if (group !in excludedStars) {
                quests.first().stars
            } else {
                0
            }

            stickyHeader(key = group) {
                QuestGroupSectionHeader(
                    group = group,
                    numberOfStars = numberOfStars,
                    expanded = isExpanded,
                    onToggleExpand = {
                        if (isExpanded) {
                            expandedGroupSections.remove(group)
                        } else {
                            expandedGroupSections.add(group)
                        }
                    }
                )
            }

            if (isExpanded) {
                items(
                    items = quests,
                    key = { it.id }
                ) { quest ->
                    Column(
                        modifier = Modifier.animateItem()
                    ) {
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
fun QuestGroupSectionHeader(
    group: QuestGroup,
    numberOfStars: Int,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onToggleExpand: () -> Unit = {},
) {
    val title = when (group) {
        QuestGroup.VILLAGE_1 -> R.string.quest_group_village_1
        QuestGroup.VILLAGE_2 -> R.string.quest_group_village_2
        QuestGroup.VILLAGE_3 -> R.string.quest_group_village_3
        QuestGroup.VILLAGE_4 -> R.string.quest_group_village_4
        QuestGroup.VILLAGE_5 -> R.string.quest_group_village_5
        QuestGroup.VILLAGE_6 -> R.string.quest_group_village_6
        QuestGroup.VILLAGE_7 -> R.string.quest_group_village_7
        QuestGroup.VILLAGE_8 -> R.string.quest_group_village_8
        QuestGroup.VILLAGE_9 -> R.string.quest_group_village_9
        QuestGroup.HR_1_1 -> R.string.quest_group_hr_1_1
        QuestGroup.HR_1_2 -> R.string.quest_group_hr_1_2
        QuestGroup.HR_1_3 -> R.string.quest_group_hr_1_3
        QuestGroup.HR_2 -> R.string.quest_group_hr_2
        QuestGroup.HR_3 -> R.string.quest_group_hr_3
        QuestGroup.HR_4 -> R.string.quest_group_hr_4
        QuestGroup.HR_5 -> R.string.quest_group_hr_5
        QuestGroup.HR_6 -> R.string.quest_group_hr_6
        QuestGroup.HR_7 -> R.string.quest_group_hr_7
        QuestGroup.HR_8 -> R.string.quest_group_hr_8
        QuestGroup.HR_9 -> R.string.quest_group_hr_9
        QuestGroup.TREASURE -> R.string.quest_group_treasure
        QuestGroup.EVENT -> R.string.quest_group_event
        QuestGroup.BEGINNER_BASIC -> R.string.quest_group_beginner_basic
        QuestGroup.BEGINNER_WEAPON -> R.string.quest_group_beginner_weapon
        QuestGroup.TRAINING_BATTLE -> R.string.quest_group_training_battle
        QuestGroup.TRAINING_SPECIAL -> R.string.quest_group_training_special
        QuestGroup.TRAINING_G -> R.string.quest_group_training_g
        QuestGroup.GROUP_PRACTICE -> R.string.quest_group_group_practice
        QuestGroup.GROUP_CHALLENGE -> R.string.quest_group_group_challenge
    }

    ListItemLayout(
        leadingContent = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.bodyLarge,
                color = if (expanded) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
        },
        headlineContent = {
            if (numberOfStars > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (group in listOf(QuestGroup.HR_7, QuestGroup.HR_8, QuestGroup.HR_9)) {
                        Text(
                            text = "G ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (expanded) {
                                MaterialTheme.colorScheme.onSecondaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                        )
                        repeat(numberOfStars - 8) {
                            Icon(
                                imageVector = Icons.TwoTone.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
                    } else {
                        repeat(numberOfStars) {
                            Icon(
                                imageVector = Icons.TwoTone.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
                    }
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
