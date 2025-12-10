package com.gaugustini.mhfudatabase.ui.userset.detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponSelection(
    weapons: List<Weapon>,
    onWeaponClick: (weaponId: Int) -> Unit = {},
    onBack: () -> Unit = {},
) {
    BackHandler { onBack() }

    Scaffold(
        topBar = { // TODO: Change top bar for filters
            TopBar(
                title = stringResource(R.string.screen_user_set_list),
                navigationType = NavigationType.BACK,
                navigation = onBack,
                openSearch = {},
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(weapons) { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onWeaponClick = onWeaponClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponSelectionPreview(
) {
    Theme {
        WeaponSelection(
            weapons = PreviewWeaponData.weaponList,
        )
    }
}
