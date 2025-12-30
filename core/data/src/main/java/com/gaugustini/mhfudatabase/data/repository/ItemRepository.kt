package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.mapper.ItemMapper
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add item usages and sources
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
        val itemWithText = itemDao.getItem(itemId, language)

        return ItemMapper.map(itemWithText)
    }

    /**
     * Returns the list of all items.
     */
    suspend fun getItemList(
        language: String,
    ): List<Item> {
        val itemsWithText = itemDao.getItemList(language)

        return ItemMapper.mapList(itemsWithText)
    }

    /**
     * Returns the list of all item combinations.
     */
    suspend fun getItemCombinationList(
        language: String,
    ): List<ItemCombination> {
        val itemCombinations = itemDao.getItemCombinationList()
        val itemIds = itemCombinations
            .flatMap { listOf(it.itemCreatedId, it.itemAId, it.itemBId) }
            .distinct()
        val itemsWithText = itemDao.getItemListByIds(itemIds, language)

        return ItemMapper.mapList(itemsWithText, itemCombinations)
    }

}
