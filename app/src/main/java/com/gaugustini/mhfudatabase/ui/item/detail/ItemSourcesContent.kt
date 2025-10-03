package com.gaugustini.mhfudatabase.ui.item.detail

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.MonsterReward
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.item.components.ItemSourceItemLocationListItem
import com.gaugustini.mhfudatabase.ui.item.components.ItemSourceMonsterRewardListItem
import com.gaugustini.mhfudatabase.ui.itemcombination.components.ItemCombinationListItem
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
    LazyColumn(
        modifier = modifier
    ) {
        if (combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                )
            }
            itemsIndexed(combinations) { index, combination ->
                ItemCombinationListItem(
                    itemCombination = combination,
                    onItemClick = onItemClick,
                )
                if (index != combinations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (locations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_location),
                )
            }
            val itemsPerLocation = locations.groupBy { it.locationName }

            itemsPerLocation.forEach { (locationName, items) ->
                val itemsPerRank = items.groupBy { it.rank }

                itemsPerRank.forEach { (rank, items) ->
                    item {
                        val rank = when (rank) {
                            Rank.LOW -> stringResource(R.string.item_rank_low)
                            Rank.HIGH -> stringResource(R.string.item_rank_high)
                            Rank.G -> stringResource(R.string.item_rank_g)
                            Rank.TREASURE -> stringResource(R.string.item_rank_treasure)
                            Rank.TRAINING -> stringResource(R.string.item_rank_training)
                        }

                        SectionHeader(
                            title = "$locationName ($rank)",
                            backgroundColor = MaterialTheme.colorScheme.surface,
                        )
                    }
                    itemsIndexed(items) { index, location ->
                        ItemSourceItemLocationListItem(
                            item = location,
                            onLocationClick = onLocationClick,
                        )
                        if (index != items.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        if (monsters.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_monster),
                )
            }
            itemsIndexed(monsters) { index, monster ->
                ItemSourceMonsterRewardListItem(
                    item = monster,
                    onMonsterClick = onMonsterClick,
                )
                if (index != monsters.lastIndex) {
                    HorizontalDivider()
                }
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
