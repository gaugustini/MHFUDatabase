package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.domain.model.Location
import com.gaugustini.mhfudatabase.util.preview.PreviewItemData.item

object PreviewLocationData {

    // Location

    val location = Location(
        id = 1,
        name = "Location",
        gatheringPoints = null,
    )

    val locationList = listOf(
        location.copy(id = 1, name = "Location 1"),
        location.copy(id = 2, name = "Location 2"),
        location.copy(id = 3, name = "Location 3"),
    )

    // Gathering Point

    val gatheringPoint = GatheringPoint(
        rank = Rank.LOW,
        area = 1,
        type = GatherType.COLLECT,
        item = item
    )

    val gatheringPointList = listOf(
        gatheringPoint.copy(type = GatherType.COLLECT),
        gatheringPoint.copy(type = GatherType.BUG),
        gatheringPoint.copy(type = GatherType.FISH),
        gatheringPoint.copy(type = GatherType.MINE),
    )

}
