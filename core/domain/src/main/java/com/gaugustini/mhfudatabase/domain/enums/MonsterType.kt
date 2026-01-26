package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the monster type.
 */
enum class MonsterType {
    /**
     * Small monsters, like Bullfango or Genprey.
     */
    SMALL,

    /**
     * Large monsters, like Rathalos or Tigrex.
     */
    LARGE;

    companion object {

        /**
         * Converts a string value to a [MonsterType].
         */
        fun fromString(string: String): MonsterType {
            return when (string) {
                "SMALL" -> SMALL
                "LARGE" -> LARGE
                else -> throw IllegalArgumentException("Invalid monster type value: $string")
            }
        }

    }
}
