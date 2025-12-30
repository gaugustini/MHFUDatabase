package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
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

}
