package com.gaugustini.mhfudatabase.ui.features.weapon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.AmmoBowgun
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.ForEachWithDivider
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewWeaponData

@Composable
fun WeaponAmmoBowgunSummary(
    ammo: AmmoBowgun,
    modifier: Modifier = Modifier,
) {
    val shots = listOf(
        ammo.normal to (R.string.weapon_ammo_bowgun_normal to ItemIconColor.WHITE),
        ammo.pierce to (R.string.weapon_ammo_bowgun_pierce to ItemIconColor.WHITE),
        ammo.pellet to (R.string.weapon_ammo_bowgun_pellet to ItemIconColor.WHITE),
        ammo.crag to (R.string.weapon_ammo_bowgun_crag to ItemIconColor.WHITE),
        ammo.clust to (R.string.weapon_ammo_bowgun_clust to ItemIconColor.WHITE),
        ammo.recovery to (R.string.weapon_ammo_bowgun_recovery to ItemIconColor.GREEN),
        ammo.poison to (R.string.weapon_ammo_bowgun_poison to ItemIconColor.PURPLE),
        ammo.paralysis to (R.string.weapon_ammo_bowgun_paralysis to ItemIconColor.YELLOW),
        ammo.sleep to (R.string.weapon_ammo_bowgun_sleep to ItemIconColor.SKY),
        ammo.flame to (R.string.weapon_ammo_bowgun_flame to ItemIconColor.RED),
        ammo.water to (R.string.weapon_ammo_bowgun_water to ItemIconColor.SKY),
        ammo.thunder to (R.string.weapon_ammo_bowgun_thunder to ItemIconColor.YELLOW),
        ammo.freeze to (R.string.weapon_ammo_bowgun_freeze to ItemIconColor.BLUE),
        ammo.dragon to (R.string.weapon_ammo_bowgun_dragon to ItemIconColor.RED),
        ammo.tranq to (R.string.weapon_ammo_bowgun_tranq to ItemIconColor.RED),
        ammo.paint to (R.string.weapon_ammo_bowgun_paint to ItemIconColor.PINK),
        ammo.demon to (R.string.weapon_ammo_bowgun_demon to ItemIconColor.RED),
        ammo.armor to (R.string.weapon_ammo_bowgun_armor to ItemIconColor.ORANGE),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        shots.ForEachWithDivider { (ammo, res) ->
            val (labelAmmo, iconColor) = res

            ListItemLayout(
                leadingContent = {
                    Image(
                        painter = painterResource(R.drawable.ic_item_shell),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = MHFUColors.getItemColor(iconColor),
                            blendMode = BlendMode.Modulate
                        ),
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(labelAmmo),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Text(
                        text = ammo.replace("-", " - "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = Dimension.Spacing.large,
                    vertical = Dimension.Spacing.medium
                ),
            )
        }

        if (ammo.rapidFire != null) {
            val shots = ammo.rapidFire!!.split("|")

            AppHDivider()

            ListItemLayout(
                leadingContent = {
                    Box(modifier = Modifier.size(Dimension.Size.extraSmall))
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.weapon_ammo_bowgun_rapid_fire),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                trailingContent = {
                    Column {
                        shots.forEach {
                            Text(
                                text = it,
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
        }
    }
}

@DevicePreviews
@Composable
fun WeaponAmmoBowgunSummaryPreview() {
    Theme {
        WeaponAmmoBowgunSummary(
            ammo = PreviewWeaponData.ammoBowgun,
        )
    }
}
