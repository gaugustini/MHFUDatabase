package com.gaugustini.mhfudatabase.ui.weapon.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme

@Composable
fun WeaponTypeList(
    modifier: Modifier = Modifier,
    onWeaponTypeClick: (WeaponType) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(WeaponType.entries) { weaponType ->
            WeaponTypeListItem(
                weaponType = weaponType,
                onWeaponTypeClick = onWeaponTypeClick,
            )
            HorizontalDivider()
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponTypeListPreview() {
    Theme {
        WeaponTypeList()
    }
}
