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
        SELECT location.*, location_text.* FROM location
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE location.id = :locationId
        """
    )
    suspend fun getLocation(locationId: Int, language: String): LocationWithText

    @Query(
        """
        SELECT location.*, location_text.* FROM location
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        """
    )
    suspend fun getLocationList(language: String): List<LocationWithText>

    @Query(
        """
        SELECT 
            location_item.*,
            item.*,
            item_text.*
        FROM location_item
        JOIN item
            ON location_item.item_id = item.id
        JOIN item_text
            ON location_item.item_id = item_text.item_id
            AND item_text.language = :language
        WHERE location_item.location_id = :locationId
        ORDER BY
            location_item.rank, location_item.area, location_item.gather_type, item_text.name
        """
    )
    suspend fun getGatherableItemsByLocationId(
        locationId: Int,
        language: String
    ): List<LocationItemWithItem>

}
