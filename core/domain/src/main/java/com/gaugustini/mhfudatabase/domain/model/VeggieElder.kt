package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents a Veggie Elder location in the game.
 *
 * @property id The ID of the table of trades.
 * @property location The location in the game.
 * @property locationArea The area of the location where the Veggie Elder can be found.
 * @property trades The list of trades available in the location.
 */
data class VeggieLocation(
    val id: Int,
    val location: Location,
    val locationArea: Int,
    val trades: List<VeggieTrade>?,
)

/**
 * Represents a trade between a player and the Veggie Elder in the game.
 *
 * @property itemTraded The item traded by the player.
 * @property itemCommon The item received from the Veggie Elder, common in the trade (80%).
 * @property itemRare The item received from the Veggie Elder, rare in the trade (20%).
 */
data class VeggieTrade(
    val itemTraded: Item,
    val itemCommon: Item,
    val itemRare: Item,
)
