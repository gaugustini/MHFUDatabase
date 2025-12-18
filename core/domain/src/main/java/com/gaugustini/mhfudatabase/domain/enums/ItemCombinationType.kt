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
}
