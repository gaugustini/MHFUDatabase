package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.VeggieDao
import com.gaugustini.mhfudatabase.data.mapper.ItemMapper
import com.gaugustini.mhfudatabase.data.mapper.VeggieMapper
import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.domain.model.VeggieTrade
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Veggie Elder.
 */
@Singleton
class VeggieRepository @Inject constructor(
    private val veggieDao: VeggieDao,
    private val itemDao: ItemDao,
) {

    /**
     * Returns the Veggie Elder location with the given ID.
     */
    suspend fun getVeggieLocation(
        veggieId: Int,
        language: String,
    ): VeggieLocation {
        return VeggieMapper.toModel(
            veggie = veggieDao.getVeggieLocation(veggieId, language),
            trades = getVeggieTradeList(veggieId, language),
        )
    }

    /**
     * Returns the list of all Veggie Elder locations.
     */
    suspend fun getVeggieLocationList(
        language: String,
    ): List<VeggieLocation> {
        return veggieDao.getVeggieLocationList(
            language = language
        ).map { VeggieMapper.toModel(it) }
    }

    /**
     * Returns the list of all trades between the player and the Veggie Elder in the given ID.
     */
    private suspend fun getVeggieTradeList(
        veggieId: Int,
        language: String,
    ): List<VeggieTrade> {
        val tradeEntities = veggieDao.getVeggieTradeList(veggieId)

        if (tradeEntities.isEmpty()) return emptyList()

        val itemIds = tradeEntities.flatMap {
            listOf(it.itemTradedId, it.itemCommonId, it.itemRareId)
        }

        val itemsById = itemDao.getItemListByItemIds(itemIds, language).associate {
            it.item.id to ItemMapper.toModel(it)
        }

        return tradeEntities.mapNotNull { entity ->
            val traded = itemsById[entity.itemTradedId]
            val common = itemsById[entity.itemCommonId]
            val rare = itemsById[entity.itemRareId]

            if (traded != null && common != null && rare != null) {
                VeggieMapper.toVeggieTrade(traded, common, rare)
            } else {
                null
            }
        }
    }

}
