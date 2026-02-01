package com.gaugustini.mhfudatabase.ui.features.item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.MonsterSource
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun MonsterSourceListItem(
    source: MonsterSource,
    modifier: Modifier = Modifier,
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            MonsterIcon(
                monsterId = source.monster.id,
                modifier = Modifier.size(Dimension.Size.medium)
            )
        },
        headlineContent = {
            Text(
                text = source.monster.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            val rank = when (source.rank) {
                Rank.LOW -> stringResource(R.string.item_rank_low)
                Rank.HIGH -> stringResource(R.string.item_rank_high)
                Rank.G -> stringResource(R.string.item_rank_g)
                Rank.TREASURE -> stringResource(R.string.item_rank_treasure)
                Rank.TRAINING -> stringResource(R.string.item_rank_training)
            }

            Text(
                text = "${source.condition} ($rank)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingContent = {
            if (source.percentage != null) {
                Text(
                    text = "${source.percentage}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onMonsterClick(source.monster.id) }
    )
}

@DevicePreviews
@Composable
fun MonsterSourceListItemPreview() {
    Theme {
        MonsterSourceListItem(
            source = PreviewItemData.monsterSource,
        )
    }
}
