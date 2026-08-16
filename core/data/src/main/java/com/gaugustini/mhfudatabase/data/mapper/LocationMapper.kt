package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationItemWithItem
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
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
        items: List<LocationItemWithItem>? = null,
        quests: List<QuestWithText>? = null,
    ): Location {
        return Location(
            id = location.location.id,
            name = location.locationText.name,
            gatheringPoints = items?.map { toGatheringPoint(it) }?.groupBy { it.rank },
            quests = quests?.map { QuestMapper.toModel(it) },
        )
    }

    fun toGatheringPoint(
        item: LocationItemWithItem,
    ): GatheringPoint {
        return GatheringPoint(
            rank = Rank.fromString(item.locationItem.rank),
            area = item.locationItem.area,
            node = item.locationItem.node,
            type = GatherType.fromString(item.locationItem.type),
            min = item.locationItem.min,
            max = item.locationItem.max,
            item = ItemMapper.toModel(ItemWithText(item.item, item.itemText)),
            percentage = item.locationItem.percentage,
        )
    }

}
