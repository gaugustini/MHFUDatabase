package com.gaugustini.mhfudatabase.ui.monster.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.data.model.ItemUsage
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterItemUsageList
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailSummaryContent(
    monster: Monster,
    items: List<ItemUsage>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimension.Padding.endContent)
    ) {
        DetailHeader(
            icon = {
                MonsterIcon(
                    monsterId = monster.id,
                )
            },
            title = monster.name,
            subtitle = monster.ecology,
            description = monster.description,
        )

        MonsterItemUsageList(
            items = items,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonsterDetailSummaryContentPreview() {
    Theme {
        MonsterDetailSummaryContent(
            monster = PreviewMonsterData.monster,
            items = PreviewMonsterData.monsterItemUsageList,
        )
    }
}
