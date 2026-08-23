package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the location daytime of a quest.
 */
enum class LocationDaytime {
    DAY,
    NIGHT;

    companion object {

        /**
         * Converts a string value to a [LocationDaytime].
         */
        fun fromString(string: String): LocationDaytime {
            return when (string) {
                "DAY" -> DAY
                "NIGHT" -> NIGHT
                else -> throw IllegalArgumentException("Invalid quest daytime value: $string")
            }
        }

    }
}