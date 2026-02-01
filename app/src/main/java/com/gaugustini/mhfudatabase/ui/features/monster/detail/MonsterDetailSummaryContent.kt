package com.gaugustini.mhfudatabase.ui.features.monster.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.ui.components.DetailHeader
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.components.icons.MonsterIcon
import com.gaugustini.mhfudatabase.ui.features.monster.components.MonsterItemEffectivenessListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun MonsterDetailSummaryContent(
    monster: Monster,
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

        monster.itemEffectiveness?.let { items ->
            SectionHeader(
                title = stringResource(R.string.monster_item_effectiveness)
            )
            items.forEach { item ->
                MonsterItemEffectivenessListItem(
                    item = item,
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun MonsterDetailSummaryContentPreview() {
    Theme {
        MonsterDetailSummaryContent(
            monster = PreviewMonsterData.monster,
        )
    }
}
