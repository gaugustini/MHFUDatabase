package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.gaugustini.mhfudatabase.domain.enums.MonsterState
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterItemEffectivenessListItem(
    item: MonsterItemEffectiveness,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = Dimension.Padding.large,
                vertical = Dimension.Padding.medium
            )
    ) {
        Text(
            text = stringResource(
                when (item.monsterState) {
                    MonsterState.NORMAL -> R.string.monster_state_normal
                    MonsterState.ENRAGED -> R.string.monster_state_enraged
                }
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        if (item.canUsePitfallTrap) {
            Image(
                painter = painterResource(R.drawable.ic_item_trap),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.GREEN),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }
        if (item.canUseShockTrap) {
            Image(
                painter = painterResource(R.drawable.ic_item_trap),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.PURPLE),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }
        if (item.canUseFlashBomb) {
            Image(
                painter = painterResource(R.drawable.ic_item_ball),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.YELLOW),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }

        if (item.canUseSonicBomb) {
            Image(
                painter = painterResource(R.drawable.ic_item_ball),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.GRAY),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }

        if (item.canUseDungBomb) {
            Image(
                painter = painterResource(R.drawable.ic_item_dung),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.YELLOW),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }

        if (item.canUseMeat) {
            Image(
                painter = painterResource(R.drawable.ic_item_meat),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MHFUColors.getItemColor(ItemIconColor.RED),
                    blendMode = BlendMode.Modulate
                ),
                modifier = Modifier.size(Dimension.Size.small)
            )
        }

        if (
            !item.canUsePitfallTrap &&
            !item.canUseShockTrap &&
            !item.canUseFlashBomb &&
            !item.canUseSonicBomb &&
            !item.canUseDungBomb &&
            !item.canUseMeat
        ) {
            Text(
                text = stringResource(R.string.monster_item_effectiveness_none),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@DevicePreviews
@Composable
fun MonsterItemEffectivenessItemPreview() {
    Theme {
        MonsterItemEffectivenessListItem(
            item = PreviewMonsterData.monsterItemEffectiveness,
        )
    }
}
