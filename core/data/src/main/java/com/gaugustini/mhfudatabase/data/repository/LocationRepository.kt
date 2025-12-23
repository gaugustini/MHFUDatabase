package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Location.
 */
@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao,
)
