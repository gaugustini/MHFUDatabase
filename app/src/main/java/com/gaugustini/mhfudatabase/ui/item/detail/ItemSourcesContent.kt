package com.gaugustini.mhfudatabase.ui.item.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.item.components.ItemLocationListItem
import com.gaugustini.mhfudatabase.ui.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.monster.components.MonsterRewardListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData
import com.gaugustini.mhfudatabase.util.preview.PreviewMonsterData

@Composable
fun ItemSourcesContent(
    combinations: List<ItemCombination>,
    locations: List<ItemLocation>,
    monsters: List<MonsterReward>,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        if (combinations.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_crafting),
            )

            combinations.forEach { combination ->
                ItemCombinationListItem(
                    itemCombination = combination,
                    onItemClick = onItemClick,
                )
                HorizontalDivider()
            }
        }

        if (locations.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_location),
            )

            locations.forEach { location ->
                ItemLocationListItem(
                    item = location,
                )
                HorizontalDivider()
            }
        }

        if (monsters.isNotEmpty()) {
            SectionHeader(
                title = stringResource(R.string.item_monster),
            )

            monsters.forEach { monster ->
                MonsterRewardListItem(
                    reward = monster,
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemSourcesContentPreview() {
    Theme {
        ItemSourcesContent(
            combinations = PreviewItemData.itemCombinationList,
            locations = PreviewItemData.itemLocationList,
            monsters = PreviewMonsterData.monsterRewardList,
        )
    }
}
