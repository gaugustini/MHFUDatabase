package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewArmorData

@Composable
fun ArmorDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorSetClick: (armorSetId: Int) -> Unit,
    onSkillClick: (skillTreeId: Int) -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: ArmorDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArmorDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onArmorSetClick = onArmorSetClick,
        onSkillClick = onSkillClick,
        onItemClick = onItemClick,
    )
}

@Composable
fun ArmorDetailScreen(
    uiState: ArmorDetailState = ArmorDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onArmorSetClick: (armorSetId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.armor?.name ?: stringResource(R.string.screen_armor_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.armor != null) {
            ArmorDetailContent(
                armor = uiState.armor,
                onArmorSetClick = onArmorSetClick,
                onSkillClick = onSkillClick,
                onItemClick = onItemClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@DevicePreviews
@Composable
fun ArmorDetailScreenPreview(
    @PreviewParameter(ArmorDetailScreenPreviewParamProvider::class) uiState: ArmorDetailState
) {
    Theme {
        ArmorDetailScreen(uiState)
    }
}

private class ArmorDetailScreenPreviewParamProvider : PreviewParameterProvider<ArmorDetailState> {

    override val values: Sequence<ArmorDetailState> = sequenceOf(
        ArmorDetailState(
            armor = PreviewArmorData.armor,
        ),
    )

}
