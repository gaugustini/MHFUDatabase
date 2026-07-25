package com.gaugustini.mhfudatabase.ui.features.weapon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.model.AmmoBow
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponAmmoBowSummary(
    ammo: AmmoBow,
    modifier: Modifier = Modifier,
) {
    val coatings = listOf(
        ammo.power to (R.string.weapon_bow_coating_power to ItemIconColor.RED),
        ammo.close to (R.string.weapon_bow_coating_close to ItemIconColor.WHITE),
        ammo.paint to (R.string.weapon_bow_coating_paint to ItemIconColor.PINK),
        ammo.poison to (R.string.weapon_bow_coating_poison to ItemIconColor.PURPLE),
        ammo.paralysis to (R.string.weapon_bow_coating_paralysis to ItemIconColor.YELLOW),
        ammo.sleep to (R.string.weapon_bow_coating_sleep to ItemIconColor.SKY)
    )
    val charges = listOf(
        ammo.charge1Type to ammo.charge1Level,
        ammo.charge2Type to ammo.charge2Level,
        ammo.charge3Type to ammo.charge3Level,
        ammo.charge4Type to ammo.charge4Level
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ListItemLayout(
            leadingContent = {
                Image(
                    painterResource(R.drawable.ic_item_bottle),
                    null,
                    colorFilter = ColorFilter.tint(
                        color = MHFUColors.getItemColor(ItemIconColor.ORANGE),
                        blendMode = BlendMode.Modulate
                    ),
                    modifier = Modifier.size(Dimension.Size.extraSmall)
                )
            },
            headlineContent = {
                Text(
                    text = stringResource(R.string.weapon_bow_coating),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.small),
                ) {
                    coatings.forEach { (hasCoating, res) ->
                        val (labelCoating, color) = res
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (hasCoating) {
                                Image(
                                    painterResource(R.drawable.ic_item_bottle),
                                    null,
                                    colorFilter = ColorFilter.tint(
                                        color = MHFUColors.getItemColor(color),
                                        blendMode = BlendMode.Modulate
                                    ),
                                    modifier = Modifier.size(Dimension.Size.extraSmall)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_ui_none),
                                    contentDescription = null,
                                    modifier = Modifier.size(Dimension.Size.extraSmall)
                                )
                            }

                            Text(
                                text = stringResource(labelCoating),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 8.sp
                            )
                        }
                    }
                }
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )

        AppHDivider()

        ListItemLayout(
            leadingContent = {
                Image(
                    painterResource(R.drawable.ic_weapon_bow),
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
                    text = stringResource(R.string.weapon_bow_charges),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingContent = {
                Column {
                    charges.forEachIndexed { index, (type, level) ->
                        if (type != null && level != null) {
                            Text(
                                text = stringResource(
                                    when (type) {
                                        WeaponAmmo.NORMAL_RAPID -> R.string.weapon_bow_charge_rapid
                                        WeaponAmmo.PIERCE -> R.string.weapon_bow_charge_pierce
                                        WeaponAmmo.PELLET_SCATTER -> R.string.weapon_bow_charge_scatter
                                        else -> R.string.user_set_none
                                    },
                                    index + 1,
                                    level
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            },
            contentPadding = PaddingValues(
                horizontal = Dimension.Spacing.large,
                vertical = Dimension.Spacing.medium
            ),
        )
    }
}

@DevicePreviews
@Composable
fun WeaponAmmoBowSummaryPreview() {
    Theme {
        WeaponAmmoBowSummary(
            ammo = PreviewWeaponData.ammoBow,
        )
    }
}
