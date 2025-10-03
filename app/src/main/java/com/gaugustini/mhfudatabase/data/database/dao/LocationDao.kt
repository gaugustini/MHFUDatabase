package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.Location

@Dao
interface LocationDao {

    // List

    @Query(
        """
        SELECT
            location.id         AS id,
            location_text.name  AS name
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
        WHERE
            location_text.language = :language
        ORDER BY name ASC
        """
    )
    suspend fun getLocationList(language: String): List<Location>

    // Detail

    @Query(
        """
        SELECT
            location.id         AS id,
            location_text.name  AS name
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
        WHERE
            location.id = :id AND
            location_text.language = :language
        """
    )
    suspend fun getLocation(id: Int, language: String): Location

    @Query(
        """
        SELECT
            item.id                     AS itemId,
            item_text.name              AS itemName,
            location_item.location_id   AS locationId,
            location_text.name          AS locationName,
            location_item.rank          AS `rank`,
            location_item.gather_type   AS type,
            location_item.area          AS area,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM location_item
        JOIN item
            ON location_item.item_id = item.id
        JOIN item_text
            ON location_item.item_id = item_text.item_id
        JOIN location_text
            ON location_item.location_id = location_text.location_id
        WHERE
            location_item.location_id = :id AND
            item_text.language = :language
        ORDER BY
            location_item.area, location_item.gather_type, item_text.name
        """
    )
    suspend fun getItemsForLocation(id: Int, language: String): List<ItemLocation>

}
