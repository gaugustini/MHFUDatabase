package com.gaugustini.mhfudatabase.ui.skill.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun SkillTreeDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    viewModel: SkillTreeDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SkillTreeDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
    )
}

@Composable
fun SkillTreeDetailScreen(
    uiState: SkillTreeDetailState = SkillTreeDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = uiState.skillTree?.name
                    ?: stringResource(R.string.screen_skill_tree_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        SkillTreeDetailContent(
            skills = uiState.skills,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkillTreeDetailPreview(
    @PreviewParameter(SkillTreeDetailScreenPreviewParameterProvider::class) uiState: SkillTreeDetailState
) {
    Theme {
        SkillTreeDetailScreen(uiState)
    }
}

private class SkillTreeDetailScreenPreviewParameterProvider :
    PreviewParameterProvider<SkillTreeDetailState> {

    override val values: Sequence<SkillTreeDetailState> = sequenceOf(
        SkillTreeDetailState(
            skillTree = PreviewSkillData.skillTree,
            skills = PreviewSkillData.skillList,
        ),
    )

}
