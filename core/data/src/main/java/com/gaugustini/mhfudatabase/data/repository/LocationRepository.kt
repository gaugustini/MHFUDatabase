package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.mapper.LocationMapper
import com.gaugustini.mhfudatabase.domain.model.Location
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add location items
/**
 * Data repository for Location.
 */
@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao,
) {

    /**
     * Returns the location with the given ID.
     */
    suspend fun getLocation(
        locationId: Int,
        language: String,
    ): Location {
        return LocationMapper.toModel(
            location = locationDao.getLocation(locationId, language),
            items = emptyMap(),
        )
    }

    /**
     * Returns the list of all locations.
     * Note: Items that can be gathered in the locations are not included.
     */
    suspend fun getLocationList(
        language: String,
    ): List<Location> {
        return locationDao.getLocationList(language).map { LocationMapper.toModel(it) }
    }

}
