package com.gaugustini.mhfudatabase.ui.features.monster.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.ui.components.ListItemLayout
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterList(
    monsters: List<Monster>,
    modifier: Modifier = Modifier,
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(monsters) { monster ->
            MonsterListItem(
                monster = monster,
                onMonsterClick = onMonsterClick,
            )
            HorizontalDivider()
        }
    }
}

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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterListPreview() {
    Theme {
        MonsterList(
            monsters = PreviewMonsterData.monsterList,
        )
    }
}
