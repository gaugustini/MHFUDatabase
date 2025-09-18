package com.gaugustini.mhfudatabase.ui.weapon.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.ui.weapon.components.WeaponListItem
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponDetailPathsContent(
    paths: List<List<Weapon>>,
    finals: List<Weapon>,
    modifier: Modifier = Modifier,
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        paths.forEachIndexed { index, path ->
            item {
                SectionHeader(
                    title = if (paths.size == 1) {
                        stringResource(R.string.weapon_path)
                    } else {
                        stringResource(R.string.weapon_path_details, index + 1)
                    }
                )
            }

            itemsIndexed(path) { index, weapon ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Spacer(modifier = Modifier.width(Dimension.Spacing.small))
                    WeaponListItem(
                        weapon = weapon,
                        onWeaponClick = { onWeaponClick(weapon.id) },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (index != path.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        item {
            SectionHeader(
                title = stringResource(R.string.weapon_finals)
            )
        }

        itemsIndexed(finals) { index, weapon ->
            WeaponListItem(
                weapon = weapon,
                onWeaponClick = { onWeaponClick(weapon.id) },
            )
            if (index != finals.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponDetailPathsContentPreview() {
    Theme {
        WeaponDetailPathsContent(
            paths = listOf(PreviewWeaponData.weaponList),
            finals = PreviewWeaponData.weaponList,
        )
    }
}
