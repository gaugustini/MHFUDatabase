package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.domain.model.VeggieTrade

object PreviewVeggieData {

    // Veggie Location

    val veggieLocation = VeggieLocation(
        tableId = 1,
        location = PreviewLocationData.location,
        locationArea = 1,
    )

    val veggieLocationList = listOf(
        veggieLocation.copy(tableId = 1, locationArea = 0),
        veggieLocation.copy(tableId = 2, locationArea = 1),
        veggieLocation.copy(tableId = 3, locationArea = 2),
    )

    // Veggie Trade

    val veggieTrade = VeggieTrade(
        itemTraded = PreviewItemData.item,
        itemCommon = PreviewItemData.item,
        itemRare = PreviewItemData.item,
    )

    val veggieTradeList = listOf(
        veggieTrade,
        veggieTrade,
        veggieTrade,
    )

}
