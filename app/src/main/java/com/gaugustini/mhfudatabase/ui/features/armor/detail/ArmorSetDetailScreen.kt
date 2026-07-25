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
fun ArmorSetDetailRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onArmorClick: (armorId: Int) -> Unit,
    onSkillClick: (skillTreeId: Int) -> Unit,
    onItemClick: (itemId: Int) -> Unit,
    viewModel: ArmorSetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArmorSetDetailScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onArmorClick = onArmorClick,
        onSkillClick = onSkillClick,
        onItemClick = onItemClick,
    )
}

@Composable
fun ArmorSetDetailScreen(
    uiState: ArmorSetDetailState = ArmorSetDetailState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onArmorClick: (armorId: Int) -> Unit = {},
    onSkillClick: (skillTreeId: Int) -> Unit = {},
    onItemClick: (itemId: Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.armorSet?.name ?: stringResource(R.string.screen_armor_detail),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (uiState.armorSet != null) {
            ArmorSetDetailContent(
                armorSet = uiState.armorSet,
                onArmorClick = onArmorClick,
                onSkillClick = onSkillClick,
                onItemClick = onItemClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@DevicePreviews
@Composable
fun ArmorSetDetailScreenPreview(
    @PreviewParameter(ArmorSetDetailScreenPreviewParamProvider::class) uiState: ArmorSetDetailState
) {
    Theme {
        ArmorSetDetailScreen(uiState)
    }
}

private class ArmorSetDetailScreenPreviewParamProvider : PreviewParameterProvider<ArmorSetDetailState> {

    override val values: Sequence<ArmorSetDetailState> = sequenceOf(
        ArmorSetDetailState(
            armorSet = PreviewArmorData.armorSet,
        ),
    )

}
