package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.model.LocationDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao,
) {

    // List

    suspend fun getLocationList(
        language: String = "en",
    ): List<Location> {
        return locationDao.getLocationList(language)
    }

    // Detail

    suspend fun getLocationDetails(
        locationId: Int,
        language: String = "en",
    ): LocationDetails {
        return LocationDetails(
            location = locationDao.getLocation(locationId, language),
            items = locationDao.getItemsForLocation(locationId, language)
        )
    }

}
