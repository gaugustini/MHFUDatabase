package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import com.gaugustini.mhfudatabase.domain.model.VeggieTrade

object PreviewVeggieData {

    // Veggie Location

    val veggieLocation = VeggieLocation(
        id = 1,
        location = PreviewLocationData.location,
        locationArea = 1,
        trades = null,
    )

    val veggieLocationList = listOf(
        veggieLocation.copy(id = 1, locationArea = 0),
        veggieLocation.copy(id = 2, locationArea = 1),
        veggieLocation.copy(id = 3, locationArea = 2),
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
