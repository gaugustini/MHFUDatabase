package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the equipment, quest or item rank.
 */
enum class Rank {
    /**
     * Low Rank
     */
    LOW,

    /**
     * High Rank
     */
    HIGH,

    /**
     * G-Rank
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
