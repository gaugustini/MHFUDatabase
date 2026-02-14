package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.filter.DecorationFilter
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.ui.features.decoration.components.DecorationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecorationSelection(
    decorations: List<Decoration>,
    maxAvailableSlots: Int = 3,
    filter: DecorationFilter = DecorationFilter(),
    onDecorationClick: (decorationId: Int) -> Unit = {},
    onFilterChange: (filter: DecorationFilter) -> Unit = {},
    onBack: () -> Unit = {},
    openSkillSelection: () -> Unit = {},
) {
    var showSearchTextField by rememberSaveable { mutableStateOf(false) }
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(true)

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearchTextField) {
                        SearchTextField(
                            onQueryChange = {
                                onFilterChange(filter.copy(name = it))
                            },
                            onDismiss = {
                                showSearchTextField = false
                                onFilterChange(filter.copy(name = ""))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.user_set_decoration_selection),
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
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
                    if (!showSearchTextField) {
                        IconButton(
                            onClick = { showSearchTextField = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                        }
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
                DecorationListItem(
                    decoration = decoration,
                    onDecorationClick = onDecorationClick
                )
            }
        }

        if (showFilterSheet) {
            DecorationFilterSheet(
                sheetState = filterSheetState,
                filter = filter,
                maxAvailableSlots = maxAvailableSlots,
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
    maxAvailableSlots: Int,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: DecorationFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
    onOpenSkillSelection: () -> Unit = {},
) {
    var skills = filter.skills ?: emptyList()
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
                            text = skill.toString(), // TODO: Change filter to use model instead of ID
                            style = MaterialTheme.typography.bodyLarge,
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

@DevicePreviews
@Composable
fun DecorationSelectionPreview() {
    Theme {
        DecorationSelection(
            decorations = PreviewDecorationData.decorationList,
        )
    }
}
