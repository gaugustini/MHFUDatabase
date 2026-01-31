package com.gaugustini.mhfudatabase.ui.features.weapon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.ElementIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SharpnessIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SlotsIcon
import com.gaugustini.mhfudatabase.ui.components.icons.SongNotesIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponSummary(
    weapon: Weapon,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ListItemLayout(
            leadingContent = {
                Image(
                    painterResource(R.drawable.ic_ui_attack),
                    null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.weapon_attack),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = if (weapon.maxAttack != null) {
                        "${weapon.attack} (${weapon.maxAttack})"
                    } else {
                        weapon.attack.toString()
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()

        ListItemLayout(
            leadingContent = {
                Image(
                    painterResource(R.drawable.ic_ui_affinity),
                    null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.weapon_affinity),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = "${weapon.affinity}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()

        ListItemLayout(
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.ic_ui_slots),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = MHFUColors.getItemColor(ItemIconColor.BLUE),
                        blendMode = BlendMode.Modulate
                    ),
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.weapon_slots),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                SlotsIcon(
                    numberOfSlots = weapon.numberOfSlots,
                    modifier = Modifier.size(
                        height = Dimension.Size.tiny,
                        width = Dimension.Size.tiny * 3
                    )
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()

        ListItemLayout(
            leadingContent = {
                Image(
                    painterResource(R.drawable.ic_ui_defense),
                    null,
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.weapon_defense),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Text(
                    text = weapon.defense.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
        HorizontalDivider()

        if (weapon.element1 != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_element),
                        null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_element),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ElementIcon(
                            element = weapon.element1!!,
                            modifier = Modifier.size(Dimension.Size.extraSmall)
                        )
                        Text(
                            text = weapon.element1Value.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )

                        if (weapon.element2 != null) {
                            Spacer(modifier = Modifier.size(Dimension.Spacing.large))
                            ElementIcon(
                                element = weapon.element2!!,
                                modifier = Modifier.size(Dimension.Size.extraSmall)
                            )
                            Text(
                                text = weapon.element2Value.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }

        if (weapon.sharpness != null && weapon.sharpnessPlus != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_sharpness),
                        null,
                        colorFilter = ColorFilter.tint(
                            color = MHFUColors.getItemColor(ItemIconColor.YELLOW),
                            blendMode = BlendMode.Modulate
                        ),
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_sharpness),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                    ) {
                        SharpnessIcon(
                            sharpness = weapon.sharpness!!,
                            height = 14.dp,
                            width = 168.dp
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "+1",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            SharpnessIcon(
                                sharpness = weapon.sharpnessPlus!!,
                                height = 14.dp,
                                width = 168.dp
                            )
                        }
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }

        if (weapon.songNotes != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_song_notes),
                        null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_song_notes),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    SongNotesIcon(
                        songNotes = weapon.songNotes!!,
                        modifier = Modifier.size(
                            height = Dimension.Size.extraSmall,
                            width = Dimension.Size.extraSmall * 3
                        )
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }

        if (weapon.shellingType != null && weapon.shellingLevel != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_shelling),
                        null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_shelling),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }

        if (weapon.reloadSpeed != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_reload_speed),
                        null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_reload_speed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Text(
                        text = stringResource(
                            when (weapon.reloadSpeed) {
                                WeaponReloadSpeed.VERY_SLOW -> R.string.weapon_reload_speed_very_slow
                                WeaponReloadSpeed.SLOW -> R.string.weapon_reload_speed_slow
                                WeaponReloadSpeed.NORMAL -> R.string.weapon_reload_speed_normal
                                WeaponReloadSpeed.FAST -> R.string.weapon_reload_speed_fast
                                WeaponReloadSpeed.VERY_FAST -> R.string.weapon_reload_speed_very_fast
                                else -> R.string.user_set_none
                            }
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }

        if (weapon.recoil != null) {
            ListItemLayout(
                leadingContent = {
                    Image(
                        painterResource(R.drawable.ic_ui_recoil),
                        null,
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_recoil),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Text(
                        text = stringResource(
                            when (weapon.recoil) {
                                WeaponRecoil.VERY_WEAK -> R.string.weapon_recoil_very_weak
                                WeaponRecoil.WEAK -> R.string.weapon_recoil_weak
                                WeaponRecoil.LIGHT -> R.string.weapon_recoil_light
                                WeaponRecoil.MODERATE -> R.string.weapon_recoil_moderate
                                else -> R.string.user_set_none
                            }
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
            HorizontalDivider()
        }
    }
}

@DevicePreviews
@Composable
fun WeaponSummaryPreview() {
    Theme {
        WeaponSummary(
            weapon = PreviewWeaponData.weapon,
        )
    }
}
