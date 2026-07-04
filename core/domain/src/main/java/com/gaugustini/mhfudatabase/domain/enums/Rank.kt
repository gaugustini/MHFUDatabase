package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the equipment, quest or item rank.
 */
enum class Rank {
    /**
     * Unranked (Guild 1~2 and "Sinking Feeling" (Village 1) and "The Taboo of Negligence!" (Village 2))
     */
    UNRANKED,

    /**
     * Low Rank (Guild 3~5 and Village 1~6)
     */
    LOW,

    /**
     * High Rank (Guild 4~8 and Village 7~9)
     */
    HIGH,

    /**
     * G-Rank (Guild G1~G3)
     */
    G,

    /**
     * Treasure Hunting
     */
    TREASURE,

    /**
     * Training School
     */
    TRAINING;

    companion object {

        /**
         * Converts a string value to a [Rank].
         */
        fun fromString(string: String): Rank {
            return when (string) {
                "UNRANKED" -> UNRANKED
                "LOW" -> LOW
                "HIGH" -> HIGH
                "G" -> G
                "TREASURE" -> TREASURE
                "TRAINING" -> TRAINING
                else -> throw IllegalArgumentException("Invalid rank value: $string")
            }
        }

    }
}
