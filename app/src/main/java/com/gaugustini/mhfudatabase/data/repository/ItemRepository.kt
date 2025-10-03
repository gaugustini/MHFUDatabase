package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemDetails
import com.gaugustini.mhfudatabase.data.model.ItemSources
import com.gaugustini.mhfudatabase.data.model.ItemUsages
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
            usages = getItemUsages(itemId, language),
            sources = getItemSources(itemId, language),
        )
    }

    private suspend fun getItemUsages(
        itemId: Int,
        language: String = "en",
    ): ItemUsages {
        return ItemUsages(
            craftRecipes = itemDao.getItemCombinationListForItemUsages(itemId, language),
            armors = itemDao.getArmorListForItemUsages(itemId, language),
            decorations = itemDao.getDecorationListForItemUsages(itemId, language),
            weapons = itemDao.getWeaponListForItemUsages(itemId, language),
        )
    }

    private suspend fun getItemSources(
        itemId: Int,
        language: String = "en",
    ): ItemSources {
        return ItemSources(
            craftRecipes = itemDao.getItemCombinationListForItemSources(itemId, language),
            locations = itemDao.getItemLocationListForItemSources(itemId, language),
            monsterRewards = itemDao.getMonsterRewardListForItemSources(itemId, language),
        )
    }

}
