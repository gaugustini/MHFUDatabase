package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemDetails
import com.gaugustini.mhfudatabase.data.model.ItemSources
import com.gaugustini.mhfudatabase.data.model.ItemUsages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List Item

    suspend fun getItemList(): List<Item> {
        val language = currentLanguage.value.code
        return itemDao.getItemList(language)
    }

    // List Combination

    suspend fun getItemCombinationList(): List<ItemCombination> {
        val language = currentLanguage.value.code
        return itemDao.getItemCombinationList(language)
    }

    // Detail

    suspend fun getItemDetails(
        itemId: Int,
    ): ItemDetails {
        val language = currentLanguage.value.code
        return ItemDetails(
            item = itemDao.getItem(itemId, language),
            usages = getItemUsages(itemId),
            sources = getItemSources(itemId),
        )
    }

    private suspend fun getItemUsages(
        itemId: Int,
    ): ItemUsages {
        val language = currentLanguage.value.code
        return ItemUsages(
            craftRecipes = itemDao.getItemCombinationListForItemUsages(itemId, language),
            armors = itemDao.getArmorListForItemUsages(itemId, language),
            decorations = itemDao.getDecorationListForItemUsages(itemId, language),
            weapons = itemDao.getWeaponListForItemUsages(itemId, language),
        )
    }

    private suspend fun getItemSources(
        itemId: Int,
    ): ItemSources {
        val language = currentLanguage.value.code
        return ItemSources(
            craftRecipes = itemDao.getItemCombinationListForItemSources(itemId, language),
            locations = itemDao.getItemLocationListForItemSources(itemId, language),
            monsterRewards = itemDao.getMonsterRewardListForItemSources(itemId, language),
        )
    }

}
