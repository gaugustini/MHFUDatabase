package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.model.LocationDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List

    suspend fun getLocationList(): List<Location> {
        val language = currentLanguage.value.code
        return locationDao.getLocationList(language)
    }

    // Detail

    suspend fun getLocationDetails(
        locationId: Int,
    ): LocationDetails {
        val language = currentLanguage.value.code
        return LocationDetails(
            location = locationDao.getLocation(locationId, language),
            items = locationDao.getItemsForLocation(locationId, language)
        )
    }

}
