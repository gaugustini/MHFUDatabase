package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.filter.DecorationFilter
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.DecorationIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.LocalIsDarkTheme
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecorationSelection(
    decorations: List<Decoration>,
    filter: DecorationFilter = DecorationFilter(),
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onFilterChange: (filter: DecorationFilter) -> Unit = {},
    onBack: () -> Unit = {},
    openSkillSelection: () -> Unit = {},
) {
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(true)

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchTextField(
                        onQueryChange = { onFilterChange(filter.copy(name = it)) },
                        onDismiss = { onFilterChange(filter.copy(name = null)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showFilterSheet = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(decorations) { decoration ->
                DecorationSelectionListItem(
                    decoration = decoration,
                    onDecorationClick = onDecorationClick
                )
            }
        }

        if (showFilterSheet) {
            DecorationFilterSheet(
                sheetState = filterSheetState,
                filter = filter,
                onFilterChange = onFilterChange,
                onDismiss = { showFilterSheet = false },
                onOpenSkillSelection = openSkillSelection,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecorationFilterSheet(
    sheetState: SheetState,
    filter: DecorationFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: DecorationFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
    onOpenSkillSelection: () -> Unit = {},
) {
    var skills = filter.skills ?: emptyList()
    val maxAvailableSlots = filter.maxAvailableSlots ?: 3
    var numberOfSlots = filter.numberOfSlots ?: emptyList()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimension.Padding.large)
        ) {
            Text(
                text = stringResource(R.string.user_set_filter_skill),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                skills.forEach { skill ->
                    SelectionContainer(
                        selected = true,
                        onSelected = {
                            skills = skills - skill
                            onFilterChange(filter.copy(skills = skills.ifEmpty { null }))
                        },
                    ) {
                        Text(
                            text = skill.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = Dimension.Padding.large)
                        )
                    }
                }
                SelectionContainer(
                    selected = false,
                    onSelected = onOpenSkillSelection,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }

            Text(
                text = stringResource(R.string.user_set_filter_number_of_slots),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                for (slot in 1..maxAvailableSlots) {
                    SelectionContainer(
                        selected = slot in numberOfSlots,
                        onSelected = {
                            numberOfSlots = if (slot in numberOfSlots) {
                                numberOfSlots - slot
                            } else {
                                numberOfSlots + slot
                            }
                            onFilterChange(filter.copy(numberOfSlots = numberOfSlots.ifEmpty { null }))
                        }
                    ) {
                        Text(
                            text = (slot).toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DecorationSelectionListItem(
    decoration: Decoration,
    modifier: Modifier = Modifier,
    onDecorationClick: (decorationId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            DecorationIcon(
                color = decoration.color,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = decoration.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Row {
                repeat(decoration.requiredSlots) {
                    SlotIcon(
                        filled = true,
                        color = if (!LocalIsDarkTheme.current && decoration.color == ItemIconColor.WHITE) {
                            Color.Gray
                        } else {
                            MHFUColors.getItemColor(decoration.color)
                        },
                        modifier = Modifier.size(Dimension.Size.tiny)
                    )
                }
            }
        },
        trailingContent = {
            decoration.skills?.let { skills ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End,
                ) {
                    skills.forEach { (skill, points) ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.large),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = skill.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = points.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Spacing.large,
            vertical = Dimension.Spacing.medium
        ),
        modifier = modifier.clickable { onDecorationClick(decoration.id) }
    )
}

@DevicePreviews
@Composable
fun DecorationSelectionPreview() {
    Theme {
        DecorationSelection(
            decorations = PreviewDecorationData.decorationList,
        )
    }
}
