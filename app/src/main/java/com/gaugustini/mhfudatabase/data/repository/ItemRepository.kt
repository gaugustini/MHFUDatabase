package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
) {

    // List Item

    suspend fun getItemList(
        language: String = "en",
    ): List<Item> {
        return itemDao.getItemList(language)
    }

    // List Combination

    suspend fun getItemCombinationList(
        language: String = "en",
    ): List<ItemCombination> {
        return itemDao.getItemCombinationList(language)
    }

    // Detail

    suspend fun getItemDetails(
        itemId: Int,
        language: String = "en",
    ): ItemDetails {
        return ItemDetails(
            item = itemDao.getItem(itemId, language),
            usages = emptyList(),
            sources = emptyList()
        )
    }

}
