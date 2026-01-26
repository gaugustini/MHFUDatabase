package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the ailment that can be inflicted on a monster.
 */
enum class MonsterAilment {
    /**
     * Knockout
     */
    KNOCKOUT,

    /**
     * Paralysis
     */
    PARALYSIS,

    /**
     * Poison
     */
    POISON,

    /**
     * Sleep
     */
    SLEEP;

    companion object {

        /**
         * Converts a string value to a [MonsterAilment].
         */
        fun fromString(string: String): MonsterAilment {
            return when (string) {
                "KNOCKOUT" -> KNOCKOUT
                "PARALYSIS" -> PARALYSIS
                "POISON" -> POISON
                "SLEEP" -> SLEEP
                else -> throw IllegalArgumentException("Invalid monster ailment value: $string")
            }
        }

    }
}
