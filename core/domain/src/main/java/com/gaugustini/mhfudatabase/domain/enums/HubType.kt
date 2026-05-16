package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents where the quest is located.
 */
enum class HubType {
    /**
     * Quests in the village.
     */
    VILLAGE,

    /**
     * Quests in the gathering hall.
     */
    GUILD,

    /**
     * Quests in the training school.
     */
    TRAINING;

    companion object {

        /**
         * Converts a string value to a [HubType].
         */
        fun fromString(string: String): HubType {
            return when (string) {
                "VILLAGE" -> VILLAGE
                "GUILD" -> GUILD
                "TRAINING" -> TRAINING
                else -> throw IllegalArgumentException("Invalid quest hub value: $string")
            }
        }

    }
}
