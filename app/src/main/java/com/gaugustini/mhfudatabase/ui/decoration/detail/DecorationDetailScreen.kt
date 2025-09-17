package com.gaugustini.mhfudatabase.ui.decoration.detail

import android.content.res.Configuration
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
import com.gaugustini.mhfudatabase.util.preview.PreviewDecorationData
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewSkillData

@Composable
fun DecorationDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
    viewModel: DecorationDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DecorationDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onSkillClick = onSkillClick,
        onItemClick = onItemClick,
    )
}

@Composable
fun DecorationDetailScreen(
    uiState: DecorationDetailState = DecorationDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = uiState.decoration?.name
                    ?: stringResource(R.string.screen_decoration_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        if (uiState.decoration != null) {
            DecorationDetailContent(
                decoration = uiState.decoration,
                skills = uiState.skills,
                recipeA = uiState.recipeA,
                recipeB = uiState.recipeB,
                onSkillClick = onSkillClick,
                onItemClick = onItemClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DecorationDetailPreview(
    @PreviewParameter(DecorationDetailScreenPreviewParamProvider::class) uiState: DecorationDetailState
) {
    Theme {
        DecorationDetailScreen(uiState)
    }
}

private class DecorationDetailScreenPreviewParamProvider :
    PreviewParameterProvider<DecorationDetailState> {

    override val values: Sequence<DecorationDetailState> = sequenceOf(
        DecorationDetailState(
            decoration = PreviewDecorationData.decoration,
            skills = PreviewSkillData.skillTreePointsList,
            recipeA = PreviewItemData.itemQuantityList,
            recipeB = PreviewItemData.itemQuantityList,
        ),
    )

}
