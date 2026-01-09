package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the bowgun recoil.
 */
enum class WeaponRecoil {
    /**
     * Very Weak
     */
    VERY_WEAK,

    /**
     * Weak
     */
    WEAK,

    /**
     * Light
     */
    LIGHT,

    /**
     * Moderate
     */
    MODERATE;

    companion object {

        /**
         * Converts a string value to a [WeaponRecoil].
         */
        fun fromString(string: String): WeaponRecoil {
            return when (string) {
                "VERY_WEAK" -> VERY_WEAK
                "WEAK" -> WEAK
                "LIGHT" -> LIGHT
                "MODERATE" -> MODERATE
                else -> throw IllegalArgumentException("Invalid weapon recoil value: $string")
            }
        }

    }
}
