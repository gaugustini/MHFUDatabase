package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.model.Location

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

}
