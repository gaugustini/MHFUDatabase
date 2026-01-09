package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the bowgun reload speed.
 */
enum class WeaponReloadSpeed {
    /**
     * Very Slow
     */
    VERY_SLOW,

    /**
     * Slow
     */
    SLOW,

    /**
     * Normal
     */
    NORMAL,

    /**
     * Fast
     */
    FAST,

    /**
     * Very Fast
     */
    VERY_FAST;

    companion object {

        /**
         * Converts a string value to a [WeaponReloadSpeed].
         */
        fun fromString(string: String): WeaponReloadSpeed {
            return when (string) {
                "VERY_SLOW" -> VERY_SLOW
                "SLOW" -> SLOW
                "NORMAL" -> NORMAL
                "FAST" -> FAST
                "VERY_FAST" -> VERY_FAST
                else -> throw IllegalArgumentException("Invalid weapon reload speed value: $string")
            }
        }

    }
}
