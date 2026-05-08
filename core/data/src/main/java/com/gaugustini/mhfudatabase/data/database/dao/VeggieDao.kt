package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.veggie.VeggieTradeEntity
import com.gaugustini.mhfudatabase.data.database.relation.VeggieWithLocationText

/**
 * [Dao] for Veggie related database operations.
 */
@Dao
interface VeggieDao {

    @Query(
        """
        SELECT
            veggie.id AS veggie_id, veggie.location_id AS veggie_location_id, veggie.location_area AS veggie_location_area,
            location_text.*
        FROM veggie
        JOIN location_text
            ON veggie.location_id = location_text.location_id
            AND location_text.language = :language
        WHERE veggie.id = :veggieId
        """
    )
    suspend fun getVeggieLocation(veggieId: Int, language: String): VeggieWithLocationText

    @Query(
        """
        SELECT
            veggie.id AS veggie_id, veggie.location_id AS veggie_location_id, veggie.location_area AS veggie_location_area,
            location_text.*
        FROM veggie
        JOIN location_text
            ON veggie.location_id = location_text.location_id
            AND location_text.language = :language
        ORDER BY location_text.name
        """
    )
    suspend fun getVeggieLocationList(language: String): List<VeggieWithLocationText>

    @Query(
        """
        SELECT
            veggie_trade.*
        FROM veggie_trade
        WHERE veggie_trade.veggie_id = :veggieId
        """
    )
    suspend fun getVeggieTradeList(veggieId: Int): List<VeggieTradeEntity>

}
