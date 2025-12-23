package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Item.
 */
@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
)
