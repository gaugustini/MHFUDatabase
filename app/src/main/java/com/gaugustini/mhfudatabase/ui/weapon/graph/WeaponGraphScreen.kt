package com.gaugustini.mhfudatabase.ui.weapon.graph

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
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponGraph
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponGraphRoute(
    navigateBack: () -> Unit,
    openSearch: () -> Unit,
    onWeaponClick: (weaponId: Int) -> Unit,
    viewModel: WeaponGraphViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeaponGraphScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        openSearch = openSearch,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
fun WeaponGraphScreen(
    uiState: WeaponGraphState = WeaponGraphState(),
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
        WeaponGraph(
            graph = uiState.nodes,
            onWeaponClick = onWeaponClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponGraphScreenPreview(
    @PreviewParameter(WeaponGraphScreenPreviewParameterProvider::class) uiState: WeaponGraphState
) {
    Theme {
        WeaponGraphScreen(uiState)
    }
}

private class WeaponGraphScreenPreviewParameterProvider :
    PreviewParameterProvider<WeaponGraphState> {

    override val values: Sequence<WeaponGraphState> = sequenceOf(
        WeaponGraphState(
            weaponType = WeaponType.GREAT_SWORD,
            nodes = PreviewWeaponData.graph,
        ),
    )

}
