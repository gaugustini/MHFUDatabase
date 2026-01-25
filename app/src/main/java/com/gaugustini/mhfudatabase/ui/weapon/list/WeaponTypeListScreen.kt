package com.gaugustini.mhfudatabase.ui.weapon.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponTypeList

@Composable
fun WeaponTypeListRoute(
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onWeaponTypeClick: (WeaponType) -> Unit,
) {
    WeaponTypeListScreen(
        openDrawer = openDrawer,
        openSearch = openSearch,
        onWeaponTypeClick = onWeaponTypeClick,
    )
}

@Composable
fun WeaponTypeListScreen(
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    onWeaponTypeClick: (WeaponType) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.screen_weapon_list),
                navigationType = NavigationType.MENU,
                navigation = openDrawer,
                openSearch = openSearch,
            )
        },
    ) { innerPadding ->
        WeaponTypeList(
            onWeaponTypeClick = onWeaponTypeClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponTypeListScreenPreview() {
    Theme {
        WeaponTypeListScreen()
    }
}
