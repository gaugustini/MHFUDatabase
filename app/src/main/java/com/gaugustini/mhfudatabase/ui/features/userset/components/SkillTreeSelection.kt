package com.gaugustini.mhfudatabase.ui.features.userset.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.filter.SkillTreeFilter
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreeListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeSelection(
    skills: List<SkillTree>,
    filter: SkillTreeFilter = SkillTreeFilter(),
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
    onFilterChange: (filter: SkillTreeFilter) -> Unit = {},
    onBack: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(true)

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            SelectionTopBar(
                scrollBehavior = scrollBehavior,
                navigateBack = onBack,
                onFilterClick = { showFilterSheet = true },
                onQueryChange = { onFilterChange(filter.copy(name = it)) },
                onDismiss = { onFilterChange(filter.copy(name = null)) },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Dimension.Padding.medium),
            userScrollEnabled = skills.isNotEmpty(),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsWithDivider(
                items = skills,
                key = { it.id }
            ) { skill ->
                SkillTreeListItem(
                    skillTree = skill,
                    onSkillTreeClick = onSkillTreeClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
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
