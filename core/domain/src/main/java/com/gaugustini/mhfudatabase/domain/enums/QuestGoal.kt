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
}
