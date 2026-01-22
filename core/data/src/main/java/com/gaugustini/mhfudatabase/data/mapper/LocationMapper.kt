package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationItem
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.domain.model.Location

/**
 * Mapper for Location entities.
 */
object LocationMapper {

    fun toModel(
        location: LocationWithText,
        items: List<LocationItem>? = null,
    ): Location {
        return Location(
            id = location.location.id,
            name = location.locationText.name,
            gatheringPoints = items?.map { toGatheringPoint(it) }?.groupBy { it.rank },
        )
    }

    fun toGatheringPoint(
        item: LocationItem,
    ): GatheringPoint {
        return GatheringPoint(
            rank = Rank.fromString(item.locationItem.rank),
            area = item.locationItem.area,
            type = GatherType.fromString(item.locationItem.gatherType),
            item = ItemMapper.toModel(ItemWithText(item.item, item.itemText)),
        )
    }

}
