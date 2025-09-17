package com.gaugustini.mhfudatabase.ui.quest.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.QuestType
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.QuestIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

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

@Composable
fun QuestListItem(
    quest: Quest,
    modifier: Modifier = Modifier,
    onQuestClick: (questId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            QuestIcon(
                goalType = quest.goalType,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = quest.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            Text(
                text = quest.goal,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingContent = {
            if (quest.questType == QuestType.KEY) {
                Text(
                    text = stringResource(R.string.quest_type_key),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = MaterialTheme.shapes.small,
                        )
                        .padding(Dimension.Padding.small)
                )
            }
            if (quest.questType == QuestType.URGENT) {
                Text(
                    text = stringResource(R.string.quest_type_urgent),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(Dimension.Padding.small)
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium
        ),
        modifier = modifier.clickable { onQuestClick(quest.id) }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuestListPreview() {
    Theme {
        QuestList(
            quests = PreviewQuestData.questList,
            expandedStarSections = setOf(5),
        )
    }
}
