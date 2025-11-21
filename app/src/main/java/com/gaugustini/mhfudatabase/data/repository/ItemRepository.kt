package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
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
        language: Language,
    ): List<Item> {
        return itemDao.getItemList(language.code)
    }

    // List Combination

    suspend fun getItemCombinationList(
        language: Language,
    ): List<ItemCombination> {
        return itemDao.getItemCombinationList(language.code)
    }

    // Detail

    suspend fun getItemDetails(
        itemId: Int,
        language: Language,
    ): ItemDetails {
        return ItemDetails(
            item = itemDao.getItem(itemId, language.code),
            usages = getItemUsages(itemId, language),
            sources = getItemSources(itemId, language),
        )
    }

    private suspend fun getItemUsages(
        itemId: Int,
        language: Language,
    ): ItemUsages {
        return ItemUsages(
            craftRecipes = itemDao.getItemCombinationListForItemUsages(itemId, language.code),
            armors = itemDao.getArmorListForItemUsages(itemId, language.code),
            decorations = itemDao.getDecorationListForItemUsages(itemId, language.code),
            weapons = itemDao.getWeaponListForItemUsages(itemId, language.code),
        )
    }

    private suspend fun getItemSources(
        itemId: Int,
        language: Language,
    ): ItemSources {
        return ItemSources(
            craftRecipes = itemDao.getItemCombinationListForItemSources(itemId, language.code),
            locations = itemDao.getItemLocationListForItemSources(itemId, language.code),
            monsterRewards = itemDao.getMonsterRewardListForItemSources(itemId, language.code),
        )
    }

}
