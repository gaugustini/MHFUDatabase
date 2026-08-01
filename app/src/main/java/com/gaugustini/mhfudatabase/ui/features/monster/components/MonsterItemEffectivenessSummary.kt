package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterItemEffectivenessSummary(
    item: MonsterItemEffectiveness,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        item.flashBomb to (R.drawable.ic_item_ball to ItemIconColor.YELLOW),
        (item.sonicBombNormal || item.sonicBombEnraged) to (R.drawable.ic_item_ball to ItemIconColor.GRAY),
        item.shockTrap to (R.drawable.ic_item_trap to ItemIconColor.PURPLE),
        (item.pitfallTrapNormal || item.pitfallTrapEnraged) to (R.drawable.ic_item_trap to ItemIconColor.GREEN),
        item.canUseMeat to (R.drawable.ic_item_meat to ItemIconColor.RED),
        item.canUseDungBomb to (R.drawable.ic_item_dung to ItemIconColor.YELLOW),
    )

    val hasTime = item.timeFlashBomb != null || item.timeShockTrap != null ||
            item.timePitfallTrapUnseen != null || item.timePitfallTrapNormal != null
            || item.timePitfallTrapEnraged != null

    val showInfoIcon = hasTime || item.sonicBombNormal != item.sonicBombEnraged ||
            item.pitfallTrapNormal != item.pitfallTrapEnraged

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        ListItemLayout(
            headlineContent = {
                Text(
                    text = stringResource(R.string.monster_item_effectiveness),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            },
            trailingContent = {
                if (showInfoIcon) {
                    IconButton(
                        onClick = { showDialog = true },
                        modifier = Modifier.size(Dimension.Size.extraSmall)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF2962FF),
                            modifier = Modifier.background(Color.White, CircleShape)
                        )
                    }
                }
            },
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimension.Padding.large)
        ) {
            items.forEach { (isEffective, res) ->
                val (iconRes, iconColor) = res

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Box {
                        Image(
                            painter = painterResource(iconRes),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                MHFUColors.getItemColor(iconColor),
                                BlendMode.Modulate
                            ),
                            alpha = if (isEffective) 1f else 0.3f,
                            modifier = Modifier
                                .size(Dimension.Size.medium)
                                .align(Alignment.Center),
                        )
                        Icon(
                            imageVector =
                                if (isEffective) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = if (isEffective) Color(0xFF30C030) else Color(0xFFC03030),
                            modifier = Modifier
                                .size(Dimension.Size.tiny)
                                .background(MaterialTheme.colorScheme.surface, CircleShape)
                                .align(Alignment.BottomEnd),
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimension.Spacing.small)
                ) {
                    item.timeFlashBomb?.let {
                        Text(
                            text = stringResource(R.string.monster_item_flash_time, it)
                        )
                    }

                    item.timeShockTrap?.let {
                        Text(
                            text = stringResource(R.string.monster_item_shock_time, it)
                        )
                    }

                    item.timePitfallTrapNormal?.let {
                        Text(
                            text = stringResource(R.string.monster_item_pitfall_time, it)
                        )
                    }
                    item.timePitfallTrapEnraged?.let {
                        Text(
                            text = stringResource(R.string.monster_item_pitfall_enraged_time, it)
                        )
                    }
                    item.timePitfallTrapUnseen?.let {
                        Text(
                            text = stringResource(R.string.monster_item_pitfall_undetected_time, it)
                        )
                    }

                    if (item.sonicBombNormal != item.sonicBombEnraged) {
                        val text =
                            if (item.sonicBombNormal)
                                R.string.monster_item_sonic_only_normal
                            else
                                R.string.monster_item_sonic_only_enraged
                        Text(
                            text = stringResource(text)
                        )
                    }

                    if (item.pitfallTrapNormal != item.pitfallTrapEnraged) {
                        val text =
                            if (item.pitfallTrapNormal)
                                R.string.monster_item_pitfall_only_normal
                            else
                                R.string.monster_item_pitfall_only_enraged
                        Text(
                            text = stringResource(text)
                        )
                    }
                }
            },
            confirmButton = {},
            onDismissRequest = { showDialog = false },
        )
    }
}

@DevicePreviews
@Composable
fun MonsterItemEffectivenessItemEffectivenessSummaryPreview() {
    Theme {
        MonsterItemEffectivenessSummary(
            item = PreviewMonsterData.monsterItemEffectiveness,
        )
    }
}
