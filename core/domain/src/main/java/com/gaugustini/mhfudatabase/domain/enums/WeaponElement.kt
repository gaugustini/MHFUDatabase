package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the weapon element.
 */
enum class WeaponElement {
    /**
     * Fire element.
     */
    FIRE,

    /**
     * Water element.
     */
    WATER,

    /**
     * Thunder element.
     */
    THUNDER,

    /**
     * Ice element.
     */
    ICE,

    /**
     * Dragon element.
     */
    DRAGON,

    /**
     * Poison status.
     */
    POISON,

    /**
     * Paralysis status.
     */
    PARALYSIS,

    /**
     * Sleep status.
     */
    SLEEP;

    companion object {

        /**
         * Converts a string value to a [WeaponElement].
         */
        fun fromString(string: String): WeaponElement {
            return when (string) {
                "FIRE" -> FIRE
                "WATER" -> WATER
                "THUNDER" -> THUNDER
                "ICE" -> ICE
                "DRAGON" -> DRAGON
                "POISON" -> POISON
                "PARALYSIS" -> PARALYSIS
                "SLEEP" -> SLEEP
                else -> throw IllegalArgumentException("Invalid weapon element value: $string")
            }
        }

    }
}
