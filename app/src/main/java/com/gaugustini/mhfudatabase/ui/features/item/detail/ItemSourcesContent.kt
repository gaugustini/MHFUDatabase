package com.gaugustini.mhfudatabase.ui.features.item.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gaugustini.mhfudatabase.R
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.ui.components.AppHDivider
import com.gaugustini.mhfudatabase.ui.components.SectionHeader
import com.gaugustini.mhfudatabase.ui.features.item.components.GatheringSourceListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.MonsterSourceListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.QuestSourceListItem
import com.gaugustini.mhfudatabase.ui.features.item.components.VeggieSourceListItem
import com.gaugustini.mhfudatabase.ui.features.itemcombination.components.ItemCombinationListItem
import com.gaugustini.mhfudatabase.ui.theme.Dimension
import com.gaugustini.mhfudatabase.ui.theme.Theme
import com.gaugustini.mhfudatabase.util.DevicePreviews
import com.gaugustini.mhfudatabase.util.animateItemExpandCollapse
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData

@Composable
fun ItemSourcesContent(
    sources: ItemSources,
    modifier: Modifier = Modifier,
    onItemClick: (itemId: Int) -> Unit = {},
    onLocationClick: (locationId: Int) -> Unit = {},
    onMonsterClick: (monsterId: Int) -> Unit = {},
    onQuestClick: (questId: Int) -> Unit = {},
    onChangePage: (ItemDetailPage) -> Unit = {},
) {
    var combinationsExpanded by rememberSaveable { mutableStateOf(true) }
    var locationsExpanded by rememberSaveable { mutableStateOf(true) }
    var monsterRewardsExpanded by rememberSaveable { mutableStateOf(true) }
    var questRewardsExpanded by rememberSaveable { mutableStateOf(true) }
    var veggieExpanded by rememberSaveable { mutableStateOf(true) }

    BackHandler {
        onChangePage(ItemDetailPage.SUMMARY)
    }

    LazyColumn(
        contentPadding = PaddingValues(Dimension.Padding.medium),
        modifier = modifier.fillMaxSize()
    ) {
        if (sources.combinations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_crafting),
                    isExpandable = true,
                    expanded = combinationsExpanded,
                    modifier = Modifier
                        .padding(bottom = if (combinationsExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (combinationsExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (combinationsExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { combinationsExpanded = !combinationsExpanded }
                )
            }
            if (combinationsExpanded) {
                itemsIndexed(
                    items = sources.combinations,
                    key = { _, combination ->
                        "comb_${combination.itemCreated.id}_${combination.itemA.id}_${combination.itemB.id}"
                    }
                ) { index, combination ->
                    val isLastItem = index == sources.combinations.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        ItemCombinationListItem(
                            combination = combination,
                            onItemClick = onItemClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (sources.locations.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_location),
                    isExpandable = true,
                    expanded = locationsExpanded,
                    modifier = Modifier
                        .padding(bottom = if (locationsExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (locationsExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (locationsExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { locationsExpanded = !locationsExpanded }
                )
            }
            if (locationsExpanded) {
                val itemsPerLocation = sources.locations.groupBy { it.location.name }
                val lastLocation = itemsPerLocation.keys.last()

                itemsPerLocation.forEach { (locationName, items) ->
                    val itemsPerRank = items.groupBy { it.rank }
                    val lastRank = itemsPerRank.keys.last()

                    itemsPerRank.forEach { (rank, items) ->
                        item {
                            val rank = when (rank) {
                                Rank.UNRANKED -> stringResource(R.string.item_rank_unranked)
                                Rank.LOW -> stringResource(R.string.item_rank_low)
                                Rank.HIGH -> stringResource(R.string.item_rank_high)
                                Rank.G -> stringResource(R.string.item_rank_g)
                                Rank.TREASURE -> stringResource(R.string.item_rank_treasure)
                                Rank.TRAINING -> stringResource(R.string.item_rank_training)
                            }

                            SectionHeader(
                                title = "$locationName ($rank)",
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemExpandCollapse(this)
                            )
                        }
                        itemsIndexed(
                            items = items,
                            key = { _, location ->
                                "loc_${location.location.id}_${location.rank}_${location.area}_${location.node}"
                            }
                        ) { index, location ->
                            val isLastItemInRank = index == items.lastIndex
                            val isLastItemInLocation = isLastItemInRank && (rank == lastRank)
                            val isGlobalLastItem = isLastItemInLocation && (locationName == lastLocation)

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = if (isGlobalLastItem) Dimension.Radius.medium else 0.dp,
                                            bottomEnd = if (isGlobalLastItem) Dimension.Radius.medium else 0.dp,
                                        )
                                    )
                                    .background(MaterialTheme.colorScheme.surface)
                                    .animateItemExpandCollapse(this)
                            ) {
                                GatheringSourceListItem(
                                    source = location,
                                    onLocationClick = onLocationClick,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (!isGlobalLastItem) {
                                    AppHDivider()
                                }
                            }
                            if (isGlobalLastItem) {
                                Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                            }
                        }
                    }
                }
            }
        }

        if (sources.monsterRewards.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_monster),
                    isExpandable = true,
                    expanded = monsterRewardsExpanded,
                    modifier = Modifier
                        .padding(bottom = if (monsterRewardsExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (monsterRewardsExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (monsterRewardsExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { monsterRewardsExpanded = !monsterRewardsExpanded }
                )
            }
            if (monsterRewardsExpanded) {
                itemsIndexed(
                    items = sources.monsterRewards,
                    key = { _, monster ->
                        "mr_${monster.monster.id}_${monster.condition}_${monster.rank}_${monster.quantity}_${monster.percentage}"
                    }
                ) { index, monster ->
                    val isLastItem = index == sources.monsterRewards.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        MonsterSourceListItem(
                            source = monster,
                            onMonsterClick = onMonsterClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
                }
            }
        }

        if (sources.questRewards.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_quest),
                    isExpandable = true,
                    expanded = questRewardsExpanded,
                    modifier = Modifier
                        .padding(bottom = if (questRewardsExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (questRewardsExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (questRewardsExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { questRewardsExpanded = !questRewardsExpanded }
                )
            }
            if (questRewardsExpanded) {
                val questPerHub = sources.questRewards.groupBy { it.quest.hubType }
                val lastHub = questPerHub.keys.last()

                questPerHub.forEach { (hub, quests) ->
                    item {
                        val hubTitle = when (hub) {
                            HubType.VILLAGE -> R.string.item_quest_hub_village
                            HubType.GUILD -> R.string.item_quest_hub_guild
                            HubType.TRAINING -> R.string.item_quest_hub_training
                        }
                        SectionHeader(
                            title = stringResource(hubTitle),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemExpandCollapse(this)
                        )
                    }
                    itemsIndexed(
                        items = quests,
                        key = { _, quest ->
                            "qr_${quest.quest.id}_${quest.condition}_${quest.quantity}_${quest.percentage}"
                        }
                    ) { index, quest ->
                        val isLastItemInHub = index == quests.lastIndex
                        val isGlobalLastItem = isLastItemInHub && (hub == lastHub)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = if (isGlobalLastItem) Dimension.Radius.medium else 0.dp,
                                        bottomEnd = if (isGlobalLastItem) Dimension.Radius.medium else 0.dp,
                                    )
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .animateItemExpandCollapse(this)
                        ) {
                            QuestSourceListItem(
                                source = quest,
                                onQuestClick = onQuestClick,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (!isGlobalLastItem) {
                                AppHDivider()
                            }
                        }
                        if (isGlobalLastItem) {
                            Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                        }
                    }
                }
            }
        }

        if (sources.veggieTrades.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(R.string.item_trade_veggie),
                    isExpandable = true,
                    expanded = veggieExpanded,
                    modifier = Modifier
                        .padding(bottom = if (veggieExpanded) 0.dp else Dimension.Padding.medium)
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimension.Radius.medium,
                                topEnd = Dimension.Radius.medium,
                                bottomStart = if (veggieExpanded) 0.dp else Dimension.Radius.medium,
                                bottomEnd = if (veggieExpanded) 0.dp else Dimension.Radius.medium,
                            )
                        )
                        .clickable { veggieExpanded = !veggieExpanded }
                )
            }
            if (veggieExpanded) {
                itemsIndexed(
                    items = sources.veggieTrades,
                    key = { _, trade ->
                        "veg_${trade.location.id}_${trade.trade.itemTraded.id}"
                    }
                ) { index, trade ->
                    val isLastItem = index == sources.veggieTrades.lastIndex

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                    bottomEnd = if (isLastItem) Dimension.Radius.medium else 0.dp,
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .animateItemExpandCollapse(this)
                    ) {
                        VeggieSourceListItem(
                            source = trade,
                            onItemClick = onItemClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isLastItem) {
                            AppHDivider()
                        }
                    }
                    if (isLastItem) {
                        Spacer(modifier = Modifier.height(Dimension.Spacing.medium))
                    }
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
