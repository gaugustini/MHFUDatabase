package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.veggie.VeggieTradeEntity

/**
 * Represents a veggie trade entity with its associated location and text entities.
 */
data class VeggieTradeWithLocation(
    @Embedded val veggieTrade: VeggieTradeEntity,
    @Embedded val locationText: LocationTextEntity,
    val area: Int,
)
