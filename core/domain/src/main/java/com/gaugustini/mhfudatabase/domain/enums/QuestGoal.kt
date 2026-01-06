package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the quest goal type.
 */
enum class QuestGoal {
    /**
     * Gather items.
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
    SPECIAL;

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
                else -> throw IllegalArgumentException("Invalid quest goal value: $string")
            }
        }

    }
}
