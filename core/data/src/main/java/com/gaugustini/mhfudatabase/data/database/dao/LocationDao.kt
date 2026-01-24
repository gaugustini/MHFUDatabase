package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.LocationItemWithItem
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText

/**
 * [Dao] for Location related database operations.
 */
@Dao
interface LocationDao {

    @Query(
        """
        SELECT
            location.*,
            location_text.*
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE location.id = :locationId
        """
    )
    suspend fun getLocation(locationId: Int, language: String): LocationWithText

    @Query(
        """
        SELECT
            location.*,
            location_text.*
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        """
    )
    suspend fun getLocationList(language: String): List<LocationWithText>

    @Query(
        """
        SELECT 
            li.location_id AS li_location_id, li.item_id AS li_item_id, li.rank AS li_rank, li.gather_type AS li_gather_type, li.area AS li_area,
            item.*,
            item_text.*
        FROM location_item li
        JOIN item
            ON li.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE li.location_id = :locationId
        ORDER BY
            li.rank, li.area, li.gather_type, item_text.name
        """
    )
    suspend fun getLocationItemsByLocationId(
        locationId: Int,
        language: String
    ): List<LocationItemWithItem>

}
