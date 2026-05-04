package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.VeggieDao
import com.gaugustini.mhfudatabase.data.database.entity.veggie.VeggieTradeEntity
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
     * Returns the list of all Veggie Elder locations.
     */
    suspend fun getVeggieLocationList(
        language: String,
    ): List<VeggieLocation> {
        return veggieDao.getVeggieLocationList(
            language = language
        ).map { VeggieMapper.toVeggieLocation(it) }
    }

    /**
     * Returns the list of all trades between the player and the Veggie Elder in the given table ID.
     */
    suspend fun getVeggieTradeList(
        tableId: Int,
        language: String,
    ): List<VeggieTrade> {
        val tradeEntities = veggieDao.getVeggieTradeList(tableId)
        return mapTradeEntities(tradeEntities, language)
    }

    /**
     * Maps a list of [VeggieTradeEntity] to a list of [VeggieTrade].
     */
    private suspend fun mapTradeEntities(
        entities: List<VeggieTradeEntity>,
        language: String,
    ): List<VeggieTrade> {
        if (entities.isEmpty()) return emptyList()

        val itemIds = entities.flatMap {
            listOf(it.itemTradedId, it.itemCommonId, it.itemRareId)
        }

        val itemsById = itemDao.getItemListByItemIds(itemIds, language).associate {
            it.item.id to ItemMapper.toModel(it)
        }

        return entities.mapNotNull { entity ->
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
