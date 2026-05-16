package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the quest type.
 */
enum class QuestType {
    /**
     * A normal quest.
     */
    NORMAL,

    /**
     * A key quest, required to unlock the urgent quest.
     */
    KEY,

    /**
     * An urgent quest, required to unlock the next quest tier.
     */
    URGENT,

    /**
     * A special quest, only used in Fatalis quests.
     */
    SPECIAL,

    /**
     * A treasure quest.
     */
    TREASURE,

    /**
     * A training quest.
     */
    TRAINING,

    /**
     * An event quest.
     */
    EVENT,

    /**
     * A challenge quest.
     */
    CHALLENGE;

    companion object {

        /**
         * Converts a string value to a [QuestType].
         */
        fun fromString(string: String): QuestType {
            return when (string) {
                "NORMAL" -> NORMAL
                "KEY" -> KEY
                "URGENT" -> URGENT
                "SPECIAL" -> SPECIAL
                "TREASURE" -> TREASURE
                "TRAINING" -> TRAINING
                "EVENT" -> EVENT
                "CHALLENGE" -> CHALLENGE
                else -> throw IllegalArgumentException("Invalid quest type value: $string")
            }
        }

    }
}
