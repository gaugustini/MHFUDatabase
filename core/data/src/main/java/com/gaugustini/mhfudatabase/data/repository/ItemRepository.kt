package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.relation.VeggieTradeWithLocation
import com.gaugustini.mhfudatabase.data.mapper.ItemCombinationMapper
import com.gaugustini.mhfudatabase.data.mapper.ItemMapper
import com.gaugustini.mhfudatabase.data.mapper.VeggieMapper
import com.gaugustini.mhfudatabase.domain.filter.ItemFilter
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.domain.model.ItemUsages
import com.gaugustini.mhfudatabase.domain.model.VeggieSource
import com.gaugustini.mhfudatabase.domain.model.VeggieUsage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Item.
 */
@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
) {

    /**
     * Returns the item with the given ID.
     */
    suspend fun getItem(
        itemId: Int,
        language: String,
    ): Item {
        return ItemMapper.toModel(
            item = itemDao.getItem(itemId, language),
            sources = getItemSources(itemId, language),
            usages = getItemUsages(itemId, language),
        )
    }

    /**
     * Returns the list of all items or filtering by [ItemFilter].
     * Note: sources and usages are not populated.
     */
    suspend fun getItemList(
        language: String,
        filter: ItemFilter = ItemFilter(),
    ): List<Item> {
        return itemDao.getItemList(
            language = language,
            name = filter.name,
            rarity = filter.rarity,
            hasRarityFilter = !filter.rarity.isNullOrEmpty(),
            icons = filter.icons?.map { it.name },
            hasIconFilter = !filter.icons.isNullOrEmpty(),
            iconColors = filter.iconColors?.map { it.name },
            hasIconColorFilter = !filter.iconColors.isNullOrEmpty(),
        ).map { ItemMapper.toModel(it) }
    }

    /**
     * Returns the list of all item combinations.
     * Note: sources and usages from items are not populated.
     */
    suspend fun getItemCombinationList(
        language: String,
    ): List<ItemCombination> {
        val entities = itemDao.getItemCombinationList()
        return mapCombinationEntities(entities, language)
    }

    /**
     * Returns the sources of the item with the given ID.
     */
    private suspend fun getItemSources(
        itemId: Int,
        language: String,
    ): ItemSources {
        val combinationEntities = itemDao.getCombinationSources(itemId)
        val combinations = mapCombinationEntities(combinationEntities, language)

        val veggieTradesEntities = itemDao.getVeggieSources(itemId, language)
        val veggieTrades = mapVeggieTradeSourceEntities(veggieTradesEntities, language)

        return ItemMapper.toItemSources(
            combinations = combinations,
            locations = itemDao.getLocationSources(itemId, language),
            monsterRewards = itemDao.getMonsterRewardSources(itemId, language),
            questRewards = itemDao.getQuestRewardSources(itemId, language),
            veggieTrades = veggieTrades,
        )
    }

    /**
     * Returns the usages of the item with the given ID.
     */
    private suspend fun getItemUsages(
        itemId: Int,
        language: String,
    ): ItemUsages {
        val combinationEntities = itemDao.getCombinationUsages(itemId)
        val combinations = mapCombinationEntities(combinationEntities, language)

        val veggieTradesEntities = itemDao.getVeggieUsages(itemId, language)
        val veggieUsages = mapVeggieTradeUsageEntities(veggieTradesEntities, language)

        return ItemMapper.toItemUsages(
            combinations = combinations,
            veggieTrades = veggieUsages,
            armors = itemDao.getArmorUsages(itemId, language),
            decorations = itemDao.getDecorationUsages(itemId, language),
            weapons = itemDao.getWeaponsUsages(itemId, language),
        )
    }

    /**
     * Maps a list of [ItemCombinationEntity] to a list of [ItemCombination].
     */
    private suspend fun mapCombinationEntities(
        entities: List<ItemCombinationEntity>,
        language: String,
    ): List<ItemCombination> {
        if (entities.isEmpty()) return emptyList()

        val itemIds = entities.flatMap {
            listOf(it.itemCreatedId, it.itemAId, it.itemBId)
        }.distinct()

        val itemsById = itemDao.getItemListByItemIds(itemIds, language).associate {
            it.item.id to ItemMapper.toModel(it)
        }

        return entities.mapNotNull { entity ->
            val created = itemsById[entity.itemCreatedId]
            val itemA = itemsById[entity.itemAId]
            val itemB = itemsById[entity.itemBId]

            if (created != null && itemA != null && itemB != null) {
                ItemCombinationMapper.toModel(entity, created, itemA, itemB)
            } else {
                null
            }
        }
    }

    /**
     * Maps a list of [VeggieTradeWithLocation] to a list of [VeggieSource].
     */
    private suspend fun mapVeggieTradeSourceEntities(
        entities: List<VeggieTradeWithLocation>,
        language: String,
    ): List<VeggieSource> {
        if (entities.isEmpty()) return emptyList()

        val itemIds = entities.flatMap {
            listOf(it.veggieTrade.itemTradedId, it.veggieTrade.itemCommonId, it.veggieTrade.itemRareId)
        }

        val itemsById = itemDao.getItemListByItemIds(itemIds, language).associate {
            it.item.id to ItemMapper.toModel(it)
        }

        return entities.mapNotNull { entity ->
            val traded = itemsById[entity.veggieTrade.itemTradedId]
            val common = itemsById[entity.veggieTrade.itemCommonId]
            val rare = itemsById[entity.veggieTrade.itemRareId]

            if (traded != null && common != null && rare != null) {
                VeggieMapper.toVeggieSource(entity, traded, common, rare)
            } else {
                null
            }
        }
    }

    /**
     * Maps a list of [VeggieTradeWithLocation] to a list of [VeggieUsage].
     */
    private suspend fun mapVeggieTradeUsageEntities(
        entities: List<VeggieTradeWithLocation>,
        language: String,
    ): List<VeggieUsage> {
        if (entities.isEmpty()) return emptyList()

        val itemIds = entities.flatMap {
            listOf(it.veggieTrade.itemTradedId, it.veggieTrade.itemCommonId, it.veggieTrade.itemRareId)
        }

        val itemsById = itemDao.getItemListByItemIds(itemIds, language).associate {
            it.item.id to ItemMapper.toModel(it)
        }

        return entities.mapNotNull { entity ->
            val traded = itemsById[entity.veggieTrade.itemTradedId]
            val common = itemsById[entity.veggieTrade.itemCommonId]
            val rare = itemsById[entity.veggieTrade.itemRareId]

            if (traded != null && common != null && rare != null) {
                VeggieMapper.toVeggieUsage(entity, traded, common, rare)
            } else {
                null
            }
        }
    }

}
