package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
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
    val items = listOf(
        item.canUseFlashBomb to (R.drawable.ic_item_ball to ItemIconColor.YELLOW),
        item.canUseSonicBomb to (R.drawable.ic_item_ball to ItemIconColor.GRAY),
        item.canUseShockTrap to (R.drawable.ic_item_trap to ItemIconColor.PURPLE),
        item.canUsePitfallTrap to (R.drawable.ic_item_trap to ItemIconColor.GREEN),
        item.canUseMeat to (R.drawable.ic_item_meat to ItemIconColor.RED),
        item.canUseDungBomb to (R.drawable.ic_item_dung to ItemIconColor.YELLOW),
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimension.Spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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
                        modifier = Modifier
                            .size(Dimension.Size.medium)
                            .align(Alignment.Center),
                        alpha = if (isEffective) 1f else 0.3f
                    )
                    Icon(
                        imageVector =
                            if (isEffective) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        modifier = Modifier
                            .size(Dimension.Size.tiny)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                            .align(Alignment.BottomEnd),
                        tint = if (isEffective) Color(0xFF30C030) else Color(0xFFC03030)
                    )
                }
            }
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
