package com.gaugustini.mhfudatabase.ui.monster.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.MonsterStateType
import com.gaugustini.mhfudatabase.data.model.MonsterItemUsage
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.MHFUColors
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterItemUsageList(
    items: List<MonsterItemUsage>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        SectionHeader(
            title = stringResource(R.string.monster_item_usage)
        )
        items.forEach { item ->
            MonsterItemUsageListItem(
                item = item,
            )
        }
    }
}

@Composable
fun MonsterItemUsageListItem(
    item: MonsterItemUsage,
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
                    MonsterStateType.NORMAL -> R.string.monster_state_normal
                    MonsterStateType.ENRAGED -> R.string.monster_state_enraged
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
                text = stringResource(R.string.monster_item_usage_none),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterItemUsagePreview() {
    Theme {
        MonsterItemUsageList(
            items = PreviewMonsterData.monsterItemUsageList,
        )
    }
}
