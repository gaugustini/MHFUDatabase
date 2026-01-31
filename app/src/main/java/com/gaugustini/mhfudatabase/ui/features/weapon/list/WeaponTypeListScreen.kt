package com.gaugustini.mhfudatabase.ui.features.weapon.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.NavigationType
import com.gaugustini.mhfudatabase.ui.components.TopBar
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WeaponType.entries.ForEachWithDivider { weaponType ->
                WeaponTypeListItem(
                    weaponType = weaponType,
                    onWeaponTypeClick = onWeaponTypeClick,
                )
            }
        }
    }
}

@Composable
fun WeaponTypeListItem(
    weaponType: WeaponType,
    modifier: Modifier = Modifier,
    onWeaponTypeClick: (WeaponType) -> Unit = {},
) {
    val name = stringResource(
        when (weaponType) {
            WeaponType.GREAT_SWORD -> R.string.weapon_great_sword
            WeaponType.LONG_SWORD -> R.string.weapon_long_sword
            WeaponType.SWORD_AND_SHIELD -> R.string.weapon_sword_and_shield
            WeaponType.DUAL_BLADES -> R.string.weapon_dual_blades
            WeaponType.HAMMER -> R.string.weapon_hammer
            WeaponType.HUNTING_HORN -> R.string.weapon_hunting_horn
            WeaponType.LANCE -> R.string.weapon_lance
            WeaponType.GUNLANCE -> R.string.weapon_gunlance
            WeaponType.LIGHT_BOWGUN -> R.string.weapon_light_bowgun
            WeaponType.HEAVY_BOWGUN -> R.string.weapon_heavy_bowgun
            WeaponType.BOW -> R.string.weapon_bow
        }
    )

    ListItemLayout(
        leadingContent = {
            WeaponIcon(
                type = weaponType,
                rarity = 1,
                modifier = Modifier.size(Dimension.Size.extraLarge)
            )
        },
        headlineContent = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onWeaponTypeClick(weaponType) }
    )
}

@DevicePreviews
@Composable
fun WeaponTypeListScreenPreview() {
    Theme {
        WeaponTypeListScreen()
    }
}
