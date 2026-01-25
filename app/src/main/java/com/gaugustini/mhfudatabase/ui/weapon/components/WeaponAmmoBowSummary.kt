package com.gaugustini.mhfudatabase.ui.weapon.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.model.AmmoBow
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponAmmoBowSummary(
    ammo: AmmoBow,
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
                    Text(
                        text = stringResource(
                            when (ammo.charge1Type) {
                                WeaponAmmo.NORMAL_RAPID -> R.string.weapon_bow_charge_rapid
                                WeaponAmmo.PIERCE -> R.string.weapon_bow_charge_pierce
                                WeaponAmmo.PELLET_SCATTER -> R.string.weapon_bow_charge_scatter
                                else -> R.string.user_set_none
                            },
                            1,
                            ammo.charge1Level
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(
                            when (ammo.charge2Type) {
                                WeaponAmmo.NORMAL_RAPID -> R.string.weapon_bow_charge_rapid
                                WeaponAmmo.PIERCE -> R.string.weapon_bow_charge_pierce
                                WeaponAmmo.PELLET_SCATTER -> R.string.weapon_bow_charge_scatter
                                else -> R.string.user_set_none
                            },
                            2,
                            ammo.charge2Level
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(
                            when (ammo.charge3Type) {
                                WeaponAmmo.NORMAL_RAPID -> R.string.weapon_bow_charge_rapid
                                WeaponAmmo.PIERCE -> R.string.weapon_bow_charge_pierce
                                WeaponAmmo.PELLET_SCATTER -> R.string.weapon_bow_charge_scatter
                                else -> R.string.user_set_none
                            },
                            3,
                            ammo.charge3Level
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    if (ammo.charge4Type != null && ammo.charge4Level != null) {
                        Text(
                            text = stringResource(
                                when (ammo.charge4Type) {
                                    WeaponAmmo.NORMAL_RAPID -> R.string.weapon_bow_charge_rapid
                                    WeaponAmmo.PIERCE -> R.string.weapon_bow_charge_pierce
                                    WeaponAmmo.PELLET_SCATTER -> R.string.weapon_bow_charge_scatter
                                    else -> R.string.user_set_none
                                },
                                4,
                                ammo.charge4Level!!
                            ),
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.power) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.RED),
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
                            text = stringResource(R.string.weapon_bow_coating_power),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.close) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.WHITE),
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
                            text = stringResource(R.string.weapon_bow_coating_close),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.paint) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.PINK),
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
                            text = stringResource(R.string.weapon_bow_coating_paint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.poison) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.PURPLE),
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
                            text = stringResource(R.string.weapon_bow_coating_poison),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.paralysis) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.YELLOW),
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
                            text = stringResource(R.string.weapon_bow_coating_paralysis),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (ammo.sleep) {
                            Image(
                                painterResource(R.drawable.ic_item_bottle),
                                null,
                                colorFilter = ColorFilter.tint(
                                    color = MHFUColors.getItemColor(ItemIconColor.SKY),
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
                            text = stringResource(R.string.weapon_bow_coating_sleep),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 8.sp
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
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WeaponAmmoBowSummaryPreview() {
    Theme {
        WeaponAmmoBowSummary(
            ammo = PreviewWeaponData.ammoBow,
        )
    }
}
