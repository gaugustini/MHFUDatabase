package com.gaugustini.mhfudatabase.ui.features.quest.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.filter.QuestFilter
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.FilterChipDropdown
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.quest.components.QuestListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

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
        onToggleExpand = viewModel::onToggleExpansion,
        onFilterChange = viewModel::onFilterChange,
        onQuestClick = onQuestClick,
    )
}

@Composable
fun QuestListScreen(
    uiState: QuestListState = QuestListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onToggleExpand: (questGroup: QuestGroup) -> Unit = {},
    onFilterChange: (filter: QuestFilter) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_quest_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
                bottomContent = {
                    QuestListFilter(
                        filter = uiState.filter,
                        onFilterChange = onFilterChange,
                    )
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        QuestList(
            quests = uiState.quests,
            expandedQuestGroup = uiState.expandedQuestGroup,
            onToggleExpand = onToggleExpand,
            onQuestClick = onQuestClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun QuestListFilter(
    filter: QuestFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: QuestFilter) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Dimension.Padding.medium),
    ) {
        FilterChipDropdown(
            selected = filter.hub != null,
            selectedItem = filter.hub,
            items = listOf(null) + HubType.entries,
            onItemSelected = { selectedHub ->
                if (selectedHub != filter.hub) {
                    onFilterChange(filter.copy(hub = selectedHub, type = null))
                }
            },
            labelProvider = { hub ->
                stringResource(
                    when (hub) {
                        HubType.VILLAGE -> R.string.quest_filter_hub_village
                        HubType.GUILD -> R.string.quest_filter_hub_guild
                        HubType.TRAINING -> R.string.quest_filter_hub_training
                        else -> R.string.quest_filter_hub_all
                    }
                )
            }
        )

        if (filter.hub != HubType.TRAINING) {
            FilterChip(
                selected = filter.type != null,
                onClick = {
                    onFilterChange(
                        filter.copy(
                            type = if (filter.type != null) null
                            else listOf(QuestType.KEY, QuestType.URGENT),
                        )
                    )
                },
                label = {
                    Text(text = stringResource(R.string.quest_filter_only_key_urgent))
                }
            )
        }
    }
}

@Composable
fun QuestList(
    quests: List<Quest>,
    expandedQuestGroup: Set<QuestGroup>,
    modifier: Modifier = Modifier,
    onToggleExpand: (questGroup: QuestGroup) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
) {
    val questGrouped = quests.groupBy { it.group }

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
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        questGrouped.forEach { (group, quests) ->
            val isExpanded = group in expandedQuestGroup
            val numberOfStars = if (group !in excludedStars) {
                quests.first().stars
            } else {
                0
            }

            item(key = group) {
                QuestGroupSectionHeader(
                    group = group,
                    numberOfStars = numberOfStars,
                    expanded = isExpanded,
                    onToggleExpand = { onToggleExpand(group) },
                    modifier = Modifier
                        .padding(bottom = if (isExpanded) 0.dp else Dimension.Padding.small)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (isExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (isExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                )
            }

            if (isExpanded) {
                itemsIndexed(
                    items = quests,
                    key = { _, quest -> quest.id }
                ) { index, quest ->
                    val isLastQuestInGroup = index == quests.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastQuestInGroup) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastQuestInGroup) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        QuestListItem(
                            quest = quest,
                            onQuestClick = onQuestClick,
                        )
                        if (!isLastQuestInGroup) {
                            AppHDivider()
                        }
                    }
                    if (isLastQuestInGroup) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.small))
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
        QuestGroup.VILLAGE_1 -> R.string.section_quest_group_village_1
        QuestGroup.VILLAGE_2 -> R.string.section_quest_group_village_2
        QuestGroup.VILLAGE_3 -> R.string.section_quest_group_village_3
        QuestGroup.VILLAGE_4 -> R.string.section_quest_group_village_4
        QuestGroup.VILLAGE_5 -> R.string.section_quest_group_village_5
        QuestGroup.VILLAGE_6 -> R.string.section_quest_group_village_6
        QuestGroup.VILLAGE_7 -> R.string.section_quest_group_village_7
        QuestGroup.VILLAGE_8 -> R.string.section_quest_group_village_8
        QuestGroup.VILLAGE_9 -> R.string.section_quest_group_village_9
        QuestGroup.HR_1_1 -> R.string.section_quest_group_hr_1_1
        QuestGroup.HR_1_2 -> R.string.section_quest_group_hr_1_2
        QuestGroup.HR_1_3 -> R.string.section_quest_group_hr_1_3
        QuestGroup.HR_2 -> R.string.section_quest_group_hr_2
        QuestGroup.HR_3 -> R.string.section_quest_group_hr_3
        QuestGroup.HR_4 -> R.string.section_quest_group_hr_4
        QuestGroup.HR_5 -> R.string.section_quest_group_hr_5
        QuestGroup.HR_6 -> R.string.section_quest_group_hr_6
        QuestGroup.HR_7 -> R.string.section_quest_group_hr_7
        QuestGroup.HR_8 -> R.string.section_quest_group_hr_8
        QuestGroup.HR_9 -> R.string.section_quest_group_hr_9
        QuestGroup.TREASURE -> R.string.section_quest_group_treasure
        QuestGroup.EVENT -> R.string.section_quest_group_event
        QuestGroup.BEGINNER_BASIC -> R.string.section_quest_group_beginner_basic
        QuestGroup.BEGINNER_WEAPON -> R.string.section_quest_group_beginner_weapon
        QuestGroup.TRAINING_BATTLE -> R.string.section_quest_group_training_battle
        QuestGroup.TRAINING_SPECIAL -> R.string.section_quest_group_training_special
        QuestGroup.TRAINING_G -> R.string.section_quest_group_training_g
        QuestGroup.GROUP_PRACTICE -> R.string.section_quest_group_group_practice
        QuestGroup.GROUP_CHALLENGE -> R.string.section_quest_group_group_challenge
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
            quests = PreviewQuestData.questList,
            expandedQuestGroup = setOf(QuestGroup.VILLAGE_1),
        ),
    )

}
