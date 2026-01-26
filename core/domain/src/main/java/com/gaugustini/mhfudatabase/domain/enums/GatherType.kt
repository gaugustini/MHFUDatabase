package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the type of gathering spot.
 */
enum class GatherType {
    /**
     * Normal gathering spot.
     */
    COLLECT,

    /**
     * Mining spot.
     */
    MINE,

    /**
     * Bug spot.
     */
    BUG,

    /**
     * Fishing spot.
     */
    FISH;

    companion object {

        /**
         * Converts a string value to a [GatherType].
         */
        fun fromString(string: String): GatherType {
            return when (string) {
                "COLLECT" -> COLLECT
                "MINE" -> MINE
                "BUG" -> BUG
                "FISH" -> FISH
                else -> throw IllegalArgumentException("Invalid gather type value: $string")
            }
        }

    }
}
