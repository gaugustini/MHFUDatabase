package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the item combination type.
 */
enum class ItemCombinationType {
    /**
     * Normal combination.
     */
    NORMAL,

    /**
     * Treasure combination, available only in treasure quests.
     */
    TREASURE,

    /**
     * Alchemy combination, available only with the Alchemy skill.
     */
    ALCHEMY;

    companion object {

        /**
         * Converts a string value to an [ItemCombinationType].
         */
        fun fromString(string: String): ItemCombinationType {
            return when (string) {
                "NORMAL" -> NORMAL
                "TREASURE" -> TREASURE
                "ALCHEMY" -> ALCHEMY
                else -> throw IllegalArgumentException("Invalid item combination type value: $string")
            }
        }

    }
}
