package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the Gunlance shelling type.
 */
enum class WeaponShelling {
    /**
     * Normal shelling.
     */
    NORMAL,

    /**
     * Long shelling.
     */
    LONG,

    /**
     * Spread shelling.
     */
    SPREAD;

    companion object {

        /**
         * Converts a string value to a [WeaponShelling].
         */
        fun fromString(string: String): WeaponShelling {
            return when (string) {
                "NORMAL" -> NORMAL
                "LONG" -> LONG
                "SPREAD" -> SPREAD
                else -> throw IllegalArgumentException("Invalid weapon shelling value: $string")
            }
        }

    }
}
