package com.gaugustini.mhfudatabase.ui.features.skill.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.skill.components.SkillTreeListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
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
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_skill_tree_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.skills) { skillTree ->
                SkillTreeListItem(
                    skillTree = skillTree,
                    onSkillTreeClick = onSkillTreeClick,
                )
                HorizontalDivider()
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
