package com.gaugustini.mhfudatabase.ui.features.weapon.tree

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.features.weapon.components.WeaponNode
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponTreeRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: WeaponTreeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeaponTreeScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun WeaponTreeScreen(
    uiState: WeaponTreeState = WeaponTreeState(),
    navigateBack: () -> Unit = {},
    openSearch: () -> Unit = {},
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    when (uiState.weaponType) {
                        WeaponType.GREAT_SWORD -> R.string.screen_weapon_great_sword
                        WeaponType.LONG_SWORD -> R.string.screen_weapon_long_sword
                        WeaponType.SWORD_AND_SHIELD -> R.string.screen_weapon_sword_and_shield
                        WeaponType.DUAL_BLADES -> R.string.screen_weapon_dual_blades
                        WeaponType.HAMMER -> R.string.screen_weapon_hammer
                        WeaponType.HUNTING_HORN -> R.string.screen_weapon_hunting_horn
                        WeaponType.LANCE -> R.string.screen_weapon_lance
                        WeaponType.GUNLANCE -> R.string.screen_weapon_gunlance
                        WeaponType.LIGHT_BOWGUN -> R.string.screen_weapon_light_bowgun
                        WeaponType.HEAVY_BOWGUN -> R.string.screen_weapon_heavy_bowgun
                        WeaponType.BOW -> R.string.screen_weapon_bow
                    }
                ),
                navigationType = NavigationType.BACK,
                navigation = navigateBack,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(uiState.nodes) { root ->
                WeaponNode(
                    node = root,
                    onWeaponClick = onWeaponClick,
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun WeaponTreeScreenPreview(
    @PreviewParameter(WeaponTreeScreenPreviewParameterProvider::class) uiState: WeaponTreeState
) {
    Theme {
        WeaponTreeScreen(uiState)
    }
}

private class WeaponTreeScreenPreviewParameterProvider : PreviewParameterProvider<WeaponTreeState> {

    override val values: Sequence<WeaponTreeState> = sequenceOf(
        WeaponTreeState(
            weaponType = WeaponType.GREAT_SWORD,
            nodes = PreviewWeaponData.graph,
        ),
    )

}
