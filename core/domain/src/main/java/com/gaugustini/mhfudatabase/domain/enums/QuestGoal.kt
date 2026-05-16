package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the quest goal, this helps to define the color of the quest icon.
 */
enum class QuestGoal {
    /**
     * Gather or Delivery items.
     */
    GATHER,

    /**
     * Hunt a monster (capture or slay).
     */
    HUNT,

    /**
     * Slay a monster.
     */
    SLAY,

    /**
     * Special goal, only used in Fatalis quests.
     */
    SPECIAL,

    /**
     * Treasure hunt.
     */
    TREASURE;

    companion object {

        /**
         * Converts a string value to a [QuestGoal].
         */
        fun fromString(string: String): QuestGoal {
            return when (string) {
                "GATHER" -> GATHER
                "HUNT" -> HUNT
                "SLAY" -> SLAY
                "SPECIAL" -> SPECIAL
                "TREASURE" -> TREASURE
                else -> throw IllegalArgumentException("Invalid quest goal value: $string")
            }
        }

    }
}
