package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.item.components.GatheringSourceListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.MonsterSourceListItem
import com.gaugustini.mhfudatabase.ui.features.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemSourcesContent(
    sources: ItemSources,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (sources.combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                )
            }
            itemsIndexed(sources.combinations) { index, combination ->
                ItemCombinationListItem(
                    combination = combination,
                    onItemClick = onItemClick,
                )
                if (index != sources.combinations.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        if (sources.locations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_location),
                )
            }
            val itemsPerLocation = sources.locations.groupBy { it.location.name }

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
                        GatheringSourceListItem(
                            source = location,
                            onLocationClick = onLocationClick,
                        )
                        if (index != items.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        if (sources.monsterRewards.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_monster),
                )
            }
            itemsIndexed(sources.monsterRewards) { index, monster ->
                MonsterSourceListItem(
                    source = monster,
                    onMonsterClick = onMonsterClick,
                )
                if (index != sources.monsterRewards.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemSourcesContentPreview() {
    Theme {
        ItemSourcesContent(
            sources = PreviewItemData.itemSources,
        )
    }
}
