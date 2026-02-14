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
import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.filter.SkillTreeFilter
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreeListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillTreeSelection(
    skills: List<SkillTree>,
    filter: SkillTreeFilter = SkillTreeFilter(),
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
    onFilterChange: (filter: SkillTreeFilter) -> Unit = {},
    onBack: () -> Unit = {},
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
                            text = stringResource(id = R.string.user_set_skill_tree_selection),
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
            items(skills) { skill ->
                SkillTreeListItem(
                    skillTree = skill,
                    onSkillTreeClick = onSkillTreeClick,
                )
            }
        }
    }

    if (showFilterSheet) {
        SkillTreeFilterSheet(
            sheetState = filterSheetState,
            filter = filter,
            onFilterChange = onFilterChange,
            onDismiss = { showFilterSheet = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillTreeFilterSheet(
    sheetState: SheetState,
    filter: SkillTreeFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (filter: SkillTreeFilter) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
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
                text = stringResource(R.string.user_set_filter_skill_category),
                style = MaterialTheme.typography.titleMedium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.Padding.medium)
            ) {
                SkillCategory.entries.forEach { category ->
                    SelectionContainer(
                        selected = category == filter.category,
                        onSelected = {
                            if (category == filter.category) {
                                onFilterChange(filter.copy(category = null))
                            } else {
                                onFilterChange(filter.copy(category = category))
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(
                                when (category) {
                                    SkillCategory.BLADE -> R.string.user_set_filter_skill_blade
                                    SkillCategory.COMBAT -> R.string.user_set_filter_skill_combat
                                    SkillCategory.FELYNE -> R.string.user_set_filter_skill_felyne
                                    SkillCategory.GATHER -> R.string.user_set_filter_skill_gather
                                    SkillCategory.GUNNER -> R.string.user_set_filter_skill_gunner
                                    SkillCategory.ITEM -> R.string.user_set_filter_skill_item
                                    SkillCategory.RESISTANCE -> R.string.user_set_filter_skill_resistance
                                    SkillCategory.STATUS -> R.string.user_set_filter_skill_status
                                }
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = Dimension.Padding.large)
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun SkillTreeSelectionPreview() {
    Theme {
        SkillTreeSelection(
            skills = PreviewSkillData.skillTreeList,
        )
    }
}
