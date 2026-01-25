package com.gaugustini.mhfudatabase.ui.weapon.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ElementIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SharpnessIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotsIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SongNotesIcon
import com.gaugustini.mhfudatabase.ui.components.icons.WeaponIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponListItem(
    weapon: Weapon,
    modifier: Modifier = Modifier,
    onWeaponClick: (weaponId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onWeaponClick(weapon.id) }
    ) {
        ListItemLayout(
            headlineContent = {
                Row {
                    Text(
                        text = weapon.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    if (weapon.priceCreate != null) {
                        Spacer(modifier = Modifier.width(Dimension.Spacing.medium))
                        Image(
                            painterResource(R.drawable.ic_ui_build),
                            null,
                            modifier = Modifier.size(Dimension.Size.tiny)
                        )
                    }
                }
            },
            trailingContent = {
                SlotsIcon(
                    numberOfSlots = weapon.numberOfSlots,
                    modifier = Modifier.size(
                        height = Dimension.Size.tiny,
                        width = Dimension.Size.tiny * 3,
                    )
                )
            },
            contentPadding = PaddingValues(
                start = Dimension.Padding.large,
                top = Dimension.Padding.medium,
                end = Dimension.Padding.large,
            ),
        )
        ListItemLayout(
            leadingContent = {
                WeaponIcon(
                    type = weapon.type,
                    rarity = weapon.rarity,
                    modifier = Modifier.size(Dimension.Size.medium)
                )
            },
            headlineContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = weapon.attack.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    if (weapon.element1 != null && weapon.element1Value != null) {
                        Spacer(modifier = Modifier.width(Dimension.Spacing.medium))
                        ElementIcon(
                            element = weapon.element1!!,
                            modifier = Modifier.size(Dimension.Size.tiny)
                        )
                        Text(
                            text = if (weapon.element1Value!! > 0) {
                                weapon.element1Value.toString()
                            } else {
                                stringResource(R.string.weapon_element_add)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    if (weapon.element2 != null && weapon.element2Value != null) {
                        Spacer(modifier = Modifier.width(Dimension.Spacing.medium))
                        ElementIcon(
                            element = weapon.element2!!,
                            modifier = Modifier.size(Dimension.Size.tiny)
                        )
                        Text(
                            text = if (weapon.element2Value!! > 0) {
                                weapon.element2Value.toString()
                            } else {
                                stringResource(R.string.weapon_element_add)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            trailingContent = {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
                ) {
                    if (weapon.sharpness != null && weapon.sharpnessPlus != null) {
                        Column {
                            SharpnessIcon(
                                sharpness = weapon.sharpness!!,
                                height = 8.dp,
                                width = 96.dp,
                            )
                            SharpnessIcon(
                                sharpness = weapon.sharpnessPlus!!,
                                height = 8.dp,
                                width = 96.dp,
                            )
                        }
                    }

                    if (weapon.songNotes != null) {
                        SongNotesIcon(
                            songNotes = weapon.songNotes!!,
                            modifier = Modifier.size(
                                height = Dimension.Size.tiny,
                                width = Dimension.Size.tiny * 3,
                            )
                        )
                    }

                    if (weapon.shellingType != null && weapon.shellingLevel != null) {
                        Text(
                            text = stringResource(
                                when (weapon.shellingType) {
                                    WeaponShelling.NORMAL -> R.string.weapon_shelling_normal
                                    WeaponShelling.LONG -> R.string.weapon_shelling_long
                                    WeaponShelling.SPREAD -> R.string.weapon_shelling_spread
                                    else -> R.string.user_set_none
                                },
                                weapon.shellingLevel!!
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    if (weapon.reloadSpeed != null) {
                        Text(
                            text = stringResource(
                                R.string.weapon_reload_detail,
                                stringResource(
                                    when (weapon.reloadSpeed) {
                                        WeaponReloadSpeed.VERY_SLOW -> R.string.weapon_reload_speed_very_slow
                                        WeaponReloadSpeed.SLOW -> R.string.weapon_reload_speed_slow
                                        WeaponReloadSpeed.NORMAL -> R.string.weapon_reload_speed_normal
                                        WeaponReloadSpeed.FAST -> R.string.weapon_reload_speed_fast
                                        WeaponReloadSpeed.VERY_FAST -> R.string.weapon_reload_speed_very_fast
                                        else -> R.string.user_set_none
                                    }
                                )
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    if (weapon.recoil != null) {
                        Text(
                            text = stringResource(
                                R.string.weapon_recoil_detail,
                                stringResource(
                                    when (weapon.recoil) {
                                        WeaponRecoil.VERY_WEAK -> R.string.weapon_recoil_very_weak
                                        WeaponRecoil.WEAK -> R.string.weapon_recoil_weak
                                        WeaponRecoil.LIGHT -> R.string.weapon_recoil_light
                                        WeaponRecoil.MODERATE -> R.string.weapon_recoil_moderate
                                        else -> R.string.user_set_none
                                    }
                                )
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium,
            ),
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponListItemPreview() {
    Theme {
        WeaponListItem(
            weapon = PreviewWeaponData.weaponGS,
        )
    }
}
