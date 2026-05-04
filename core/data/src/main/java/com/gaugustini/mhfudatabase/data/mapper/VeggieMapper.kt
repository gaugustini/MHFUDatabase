package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.VeggieWithLocationText
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.domain.model.VeggieTrade

/**
 * Mapper for Veggie Elder entities.
 */
object VeggieMapper {

    fun toVeggieLocation(
        veggie: VeggieWithLocationText,
    ): VeggieLocation {
        return VeggieLocation(
            tableId = veggie.veggie.id,
            location = LocationMapper.toModel(
                LocationWithText(
                    LocationEntity(veggie.veggie.locationId),
                    veggie.locationText
                )
            ),
            locationArea = veggie.veggie.locationArea,
        )
    }

    fun toVeggieTrade(
        itemTraded: Item,
        itemCommon: Item,
        itemRare: Item,
    ): VeggieTrade {
        return VeggieTrade(
            itemTraded = itemTraded,
            itemCommon = itemCommon,
            itemRare = itemRare,
        )
    }

}
