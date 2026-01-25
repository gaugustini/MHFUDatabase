package com.gaugustini.mhfudatabase.ui.quest.detail

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
import com.gaugustini.mhfudatabase.util.preview.PreviewQuestData

@Composable
fun QuestDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onLocationClick: (locationId: Int) -> Unit,
    onMonsterClick: (monsterId: Int) -> Unit,
    viewModel: QuestDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuestDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onLocationClick = onLocationClick,
        onMonsterClick = onMonsterClick,
    )
}

@Composable
fun QuestDetailScreen(
    uiState: QuestDetailState = QuestDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = uiState.quest?.name ?: stringResource(R.string.screen_quest_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        if (uiState.quest != null) {
            QuestDetailContent(
                quest = uiState.quest,
                onLocationClick = onLocationClick,
                onMonsterClick = onMonsterClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuestDetailPreview(
    @PreviewParameter(QuestDetailScreenPreviewParamProvider::class) uiState: QuestDetailState
) {
    Theme {
        QuestDetailScreen(uiState)
    }
}

private class QuestDetailScreenPreviewParamProvider : PreviewParameterProvider<QuestDetailState> {

    override val values: Sequence<QuestDetailState> = sequenceOf(
        QuestDetailState(
            quest = PreviewQuestData.quest,
        ),
    )

}
