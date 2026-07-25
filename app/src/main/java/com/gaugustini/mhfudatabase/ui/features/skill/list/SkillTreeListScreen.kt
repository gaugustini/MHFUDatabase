package com.gaugustini.mhfudatabase.ui.features.skill.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreeListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.itemsWithDivider
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onSkillTreeClick: (skillTreeId: Int) -> Unit,
    viewModel: SkillTreeListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SkillTreeListScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        openSearch = openSearch,
        onSkillTreeClick = onSkillTreeClick,
    )
}

@Composable
fun SkillTreeListScreen(
    uiState: SkillTreeListState = SkillTreeListState(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onSkillTreeClick: (skillTreeId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_skill_tree_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(Dimension.Padding.medium),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsWithDivider(
                items = uiState.skills,
                key = { it.id }
            ) { skillTree ->
                SkillTreeListItem(
                    skillTree = skillTree,
                    onSkillTreeClick = onSkillTreeClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimension.Radius.small))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun SkillTreeListScreenPreview(
    @PreviewParameter(SkillTreeListScreenPreviewParamProvider::class) uiState: SkillTreeListState
) {
    Theme {
        SkillTreeListScreen(uiState)
    }
}

private class SkillTreeListScreenPreviewParamProvider :
    PreviewParameterProvider<SkillTreeListState> {

    override val values: Sequence<SkillTreeListState> = sequenceOf(
        SkillTreeListState(
            skills = PreviewSkillData.skillTreeList,
        ),
    )

}
