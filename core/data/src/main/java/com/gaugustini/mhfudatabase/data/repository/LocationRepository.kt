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
        val locationWithText = locationDao.getLocation(locationId, language)

        return LocationMapper.toModel(
            location = locationWithText,
            items = emptyMap(),
        )
    }

    /**
     * Returns the list of all locations.
     */
    suspend fun getLocationList(
        language: String,
    ): List<Location> {
        val locationsWithText = locationDao.getLocationList(language)

        return locationsWithText.map {
            LocationMapper.toModel(
                location = it,
                items = emptyMap(),
            )
        }
    }

}
