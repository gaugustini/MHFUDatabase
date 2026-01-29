package com.gaugustini.mhfudatabase.ui.features.monster.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterListItem(
    monster: Monster,
    modifier: Modifier = Modifier,
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    ListItemLayout(
        leadingContent = {
            MonsterIcon(
                monsterId = monster.id,
                modifier = Modifier.size(Dimension.Size.extraLarge)
            )
        },
        headlineContent = {
            Text(
                text = monster.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        supportingContent = {
            Text(
                text = monster.ecology,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        contentPadding = PaddingValues(
            horizontal = Dimension.Padding.large,
            vertical = Dimension.Padding.medium,
        ),
        modifier = modifier.clickable { onMonsterClick(monster.id) }
    )
}

@DevicePreviews
@Composable
fun MonsterListItemPreview() {
    Theme {
        MonsterListItem(
            monster = PreviewMonsterData.monster,
        )
    }
}
